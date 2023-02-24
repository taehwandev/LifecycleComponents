package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.view.event.api.ViewInteractionStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Suppress("unused")
class MockFlowLifecycleListener : LifecycleListener(MockLifecycleSource()) {
    @JvmField
    var executedUnitCount = 0

    @JvmField
    var executedFlowCount = 0

    @CreatedToDestroy
    fun executeUnit() {
        executedUnitCount++
    }

    @CreatedToDestroy
    fun executeFlow(@Suppress("UNUSED_PARAMETER") viewInteractionStream: ViewInteractionStream): Flow<Boolean> {
        executedFlowCount++
        return flowOf(true)
    }

    @ResumedToPause
    fun executeFlowResume(@Suppress("UNUSED_PARAMETER") viewInteractionStream: ViewInteractionStream): Flow<Boolean> {
        executedFlowCount++
        return flowOf(true)
    }

}
