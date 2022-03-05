package io.androidalatan.coroutine.api

import io.androidalatan.coroutine.dispatcher.api.assertion.MockDispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CoroutineScopeManagerTest {

    @Test
    fun `cancel test`() = runBlocking {
        val coroutineScope = MockDispatcherProvider().coroutineScope
        val job = coroutineScope.launch {
            Assertions.assertTrue(isActive)
            delay(1_000)
        }

        job.cancel()
        Assertions.assertFalse(job.isActive)
        Assertions.assertTrue(job.isCancelled)
        Assertions.assertTrue(job.isCompleted)
    }
}