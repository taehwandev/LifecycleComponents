package io.androidalatan.backkey.handler.api

interface BackKeyObserver {
    fun initIfNeed(handlerCallbacks: () -> List<BackKeyHandlerStream.Callback>)
    fun deinit()
}