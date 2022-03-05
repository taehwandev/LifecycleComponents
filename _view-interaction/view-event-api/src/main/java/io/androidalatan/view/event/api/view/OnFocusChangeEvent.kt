package io.androidalatan.view.event.api.view

interface OnFocusChangeEvent {
    fun registerOnFocusChangeEvent(callback: Callback)
    fun unregisterOnFocusChangeEvent(callback: Callback)

    fun interface Callback {
        fun onFocusChange(focused: Boolean)
    }
}