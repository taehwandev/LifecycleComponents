package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.coroutine.handler.annotations.RunDispatcherUI
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.annotations.sync.OnDestroy
import io.androidalatan.lifecycle.handler.annotations.sync.OnPause
import io.androidalatan.lifecycle.handler.annotations.sync.OnResumed
import io.androidalatan.lifecycle.handler.api.LifecycleListener

class MockCoroutineLifecycleListener : LifecycleListener(MockLifecycleSource()) {

    @JvmField
    var executedCount = 0

    @OnCreated
    suspend fun execute() {
        executedCount++
    }

    @OnResumed
    @RunDispatcherUI
    suspend fun resume() {
        executedCount++
    }

    @OnPause
    suspend fun pause() {
        executedCount++
    }

    @OnDestroy
    suspend fun clear() {
        executedCount = 0
    }
}