package io.androidalatan.lifecycle.handler.invokeradapter.flow

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FlowInvokeAdapterTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val adapter = FlowInvokeAdapter(TestCoroutineScope()) {}

    @Test
    fun acceptableInvoke() {
        Assertions.assertTrue(adapter.acceptableInvoke(Flow::class.java))

        Assertions.assertFalse(adapter.acceptableInvoke(Observable::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Flowable::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Single::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Maybe::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Completable::class.java))

        Assertions.assertFalse(adapter.acceptableInvoke(Unit::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Int::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(Boolean::class.java))
        Assertions.assertFalse(adapter.acceptableInvoke(String::class.java))
    }

    @Test
    fun `convertInvoke proper type and body`() {
        var count = 0
        runBlocking {
            val job = adapter
                .convertInvoke {
                    flow {
                        emit(count++)
                    }
                }
            job.join()

            adapter.convertRevoke(job)
            Assertions.assertFalse(job.isActive)
        }
        Assertions.assertEquals(1, count)
    }

    @Test
    fun acceptableRevoke() {
        Assertions.assertTrue(adapter.acceptableRevoke(Job()))

        Assertions.assertFalse(adapter.acceptableRevoke(Disposable.disposed()))

        Assertions.assertFalse(adapter.acceptableRevoke(Observable.just(true)))
        Assertions.assertFalse(adapter.acceptableRevoke(Flowable.just(true)))
        Assertions.assertFalse(adapter.acceptableRevoke(Single.just(true)))
        Assertions.assertFalse(adapter.acceptableRevoke(Maybe.just(true)))
        Assertions.assertFalse(adapter.acceptableRevoke(Completable.complete()))

        Assertions.assertFalse(adapter.acceptableRevoke(Unit::class.java))
        Assertions.assertFalse(adapter.acceptableRevoke(Int::class.java))
        Assertions.assertFalse(adapter.acceptableRevoke(Boolean::class.java))
        Assertions.assertFalse(adapter.acceptableRevoke(String::class.java))
    }

    @Test
    fun `convertRevoke disposable`() {
        val job = Job()
        adapter.convertRevoke(
            runBlocking {
                job
            }
        )
        Assertions.assertTrue(job.isCancelled)
    }

    @Test
    fun `convertRevoke non disposable`() {
        val job = Job()
        adapter.convertRevoke(Unit)
        Assertions.assertFalse(job.isCancelled)
    }
}