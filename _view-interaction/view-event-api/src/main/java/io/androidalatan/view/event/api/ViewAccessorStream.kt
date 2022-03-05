package io.androidalatan.view.event.api

interface ViewAccessorStream {
    fun registerCallback(callback: Callback)
    fun unregisterCallback(callback: Callback)

    fun interface Callback {
        fun onViewAccessor(viewAccessor: ViewAccessor)
    }
}