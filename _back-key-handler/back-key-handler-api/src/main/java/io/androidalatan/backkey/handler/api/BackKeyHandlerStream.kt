package io.androidalatan.backkey.handler.api

interface BackKeyHandlerStream {
    fun registerCallback(callback: Callback)
    fun unregisterCallback(callback: Callback)

    fun interface Callback {
        fun onCallback()
    }
}