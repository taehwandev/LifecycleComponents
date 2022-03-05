package io.androidalatan.view.event.api.view

interface OnLongClickEvent {
    fun registerOnLongClickEvent(callback: Callback)
    fun unregisterOnLongClickEvent(callback: Callback)

    fun interface Callback {
        fun onLongClick()
    }
}