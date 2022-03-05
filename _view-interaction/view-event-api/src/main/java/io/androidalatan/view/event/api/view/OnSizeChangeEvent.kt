package io.androidalatan.view.event.api.view

interface OnSizeChangeEvent {

    fun registerOnSizeChangeCallback(callback: Callback)
    fun unregisterOnSizeChangeCallback(callback: Callback)

    fun interface Callback {
        fun onSizeChange(size: ViewSize)
    }

    data class ViewSize(val width: Int, val height: Int)
}