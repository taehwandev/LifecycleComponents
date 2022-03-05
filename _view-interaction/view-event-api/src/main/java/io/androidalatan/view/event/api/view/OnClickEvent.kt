package io.androidalatan.view.event.api.view

interface OnClickEvent {
    fun registerOnClickEvent(callback: Callback)
    fun unregisterOnClickEvent(callback: Callback)

    fun interface Callback {
        fun onClick()
    }
}