package io.androidalatan.backkey.handler

import androidx.annotation.VisibleForTesting
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.backkey.handler.api.BackKeyObserver

class BackKeyHandlerStreamImpl : BackKeyHandlerStream {
    @VisibleForTesting
    internal var callbacks = mutableListOf<BackKeyHandlerStream.Callback>()

    @VisibleForTesting
    internal var backKeyObserver: BackKeyObserver? = null

    fun setBackKeyObserver(backKeyObserver: BackKeyObserver) {
        this.backKeyObserver = backKeyObserver
    }

    override fun registerCallback(callback: BackKeyHandlerStream.Callback) {
        synchronized(this) {
            val requireActivate = callbacks.size == 0
            callbacks.add(callback)
            if (requireActivate) {
                backKeyObserver?.initIfNeed { callbacks }
            }
        }
    }

    override fun unregisterCallback(callback: BackKeyHandlerStream.Callback) {
        synchronized(this) {
            callbacks.remove(callback)
            if (callbacks.size == 0) {
                backKeyObserver?.deinit()
            }
        }
    }
}