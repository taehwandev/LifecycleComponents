package io.androidalatan.view.event.api

interface ViewInteractionStream {

    fun registerCallback(callback: Callback)
    fun unregisterCallback(callback: Callback)

    fun interface Callback {
        fun onCallback(controller: ViewInteractionController)
    }
}
