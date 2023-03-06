package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.internal.ListenerAnalyzer
import io.androidalatan.lifecycle.handler.internal.invoke.model.InvokeInfo
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.invokeradapter.flow.FlowInvokeAdapter
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.impl.ViewInteractionStreamImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.reflect.KClass

internal class AsyncInvokerManagerTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val manager =
        AsyncInvokerManager(
            mapOf(ViewInteractionStream::class.java to ViewInteractionStreamImpl()),
            listOf(FlowInvokeAdapter(TestScope()) {})
        )
    private val analyzer = ListenerAnalyzer()
    private val caller = Any()

    @Test
    fun addMethods() {
        val lifecycleListener = MockFlowLifecycleListener()
        val methods = analyzer.analyze(lifecycleListener)

        manager.addMethods(caller, lifecycleListener, methods.toList())

        Assertions.assertEquals(1, manager.invokers.size)
        Assertions.assertEquals(2, manager.invokers[lifecycleListener]!!.size)
        Assertions.assertEquals(2, manager.invokers[lifecycleListener]!![CreatedToDestroy::class]!!.size)
        Assertions.assertEquals(1, manager.invokers[lifecycleListener]!![ResumedToPause::class]!!.size)

        val analyzedMethods = methods.map { analyzedMethodInfo -> analyzedMethodInfo.method }
        manager.invokers[lifecycleListener]!![CreatedToDestroy::class]!!.all { invokeInfo -> analyzedMethods.contains(invokeInfo.method) }
        manager.invokers[lifecycleListener]!![ResumedToPause::class]!!.all { invokeInfo -> analyzedMethods.contains(invokeInfo.method) }
    }

    @Test
    fun removeMethodsOf() {
        val lifecycleListener = MockFlowLifecycleListener()
        val methods = analyzer.analyze(lifecycleListener)
        manager.addMethods(caller, lifecycleListener, methods.toList())
        val invokers = manager.invokers
        Assertions.assertEquals(1, invokers.size)

        val invokerMap: MutableMap<KClass<out Annotation>, MutableList<InvokeInfo>> =
            invokers[lifecycleListener] ?: throw fail { "There should be cached objects" }

        val invokerForCreatedToDestroy = invokerMap[CreatedToDestroy::class]
        val invokerForResumeToPause = invokerMap[ResumedToPause::class]
        Assertions.assertEquals(2, invokerForCreatedToDestroy?.size)
        Assertions.assertEquals(1, invokerForResumeToPause?.size)

        manager.removeMethodsOf(this, lifecycleListener)
        Assertions.assertEquals(0, invokerForCreatedToDestroy?.size)
        Assertions.assertEquals(0, invokerForResumeToPause?.size)
        Assertions.assertEquals(0, invokers.size)
        Assertions.assertEquals(0, invokerMap.size)

    }

    @Test
    fun execute() = runTest {
        val lifecycleListener = MockFlowLifecycleListener()
        Assertions.assertEquals(0, lifecycleListener.executedUnitCount)
        Assertions.assertEquals(0, lifecycleListener.executedFlowCount)
        val methods = analyzer.analyze(lifecycleListener)
        manager.addMethods(caller, lifecycleListener, methods.toList())

        manager.execute(caller, LifecycleStatus.ON_CREATED)
        manager.execute(caller, LifecycleStatus.ON_RESUMED)
        Assertions.assertEquals(1, manager.invokedMap.size)
        Assertions.assertEquals(1, manager.invokedMap[lifecycleListener]!![CreatedToDestroy::class]!!.size)
        Assertions.assertEquals(1, manager.invokedMap[lifecycleListener]!![ResumedToPause::class]!!.size)
        Assertions.assertFalse(Unit in manager.invokedMap[lifecycleListener]!![CreatedToDestroy::class]!!)
        Assertions.assertTrue(manager.invokedMap[lifecycleListener]!![CreatedToDestroy::class]!!.first() is Job)

        Assertions.assertEquals(0, lifecycleListener.executedUnitCount)
        Assertions.assertEquals(2, lifecycleListener.executedFlowCount)

        manager.execute(caller, LifecycleStatus.ON_PAUSE)
        Assertions.assertEquals(0, manager.invokedMap[lifecycleListener]!![ResumedToPause::class]!!.size)
        Assertions.assertEquals(1, manager.invokedMap[lifecycleListener]!![CreatedToDestroy::class]!!.size)

        manager.execute(caller, LifecycleStatus.ON_DESTROY)
        Assertions.assertEquals(0, manager.invokedMap.size)
    }

    @Test
    fun executeMissingEvent() {
        val lifecycleListener = MockFlowLifecycleListener()
        Assertions.assertEquals(0, lifecycleListener.executedUnitCount)
        Assertions.assertEquals(0, lifecycleListener.executedFlowCount)
        val methods = analyzer.analyze(lifecycleListener)
        manager.addMethods(caller, lifecycleListener, methods.toList())

        manager.executeMissingEvent(caller, lifecycleListener, LifecycleStatus.ON_CREATED)
    }
}