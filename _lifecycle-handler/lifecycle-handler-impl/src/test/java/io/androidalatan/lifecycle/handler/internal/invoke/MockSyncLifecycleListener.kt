package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource

class MockSyncLifecycleListener : LifecycleListener(MockLifecycleSource()) {
    @JvmField
    var executedCount = 0

    @OnCreated
    fun execute() {
        executedCount++
    }
}