package io.androidalatan.backkey.handler.assertion

import androidx.annotation.VisibleForTesting
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream

class MockBackKeyHandlerStream : BackKeyHandlerStream {

    @VisibleForTesting
    internal var callbacks = mutableListOf<BackKeyHandlerStream.Callback>()

    fun executeCallbacks() {
        callbacks.forEach { callback ->
            callback.onCallback()
        }
    }

    override fun registerCallback(callback: BackKeyHandlerStream.Callback) {
        callbacks.add(callback)
    }

    override fun unregisterCallback(callback: BackKeyHandlerStream.Callback) {
        callbacks.remove(callback)
    }
}