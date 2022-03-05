package io.androidalatan.view.event.legacy

import io.androidalatan.view.event.api.ViewAccessor
import io.androidalatan.view.event.api.ViewAccessorStream

class ViewAccessorStreamImpl : ViewAccessorStream {
    private var viewAccessor: ViewAccessor? = null
    private val callbacks = mutableListOf<ViewAccessorStream.Callback>()
    override fun registerCallback(callback: ViewAccessorStream.Callback) {
        callbacks.add(callback)
        viewAccessor?.let { callback.onViewAccessor(it) }
    }

    override fun unregisterCallback(callback: ViewAccessorStream.Callback) {
        callbacks.remove(callback)
    }

    fun setViewAccessor(viewAccessor: ViewAccessor) {
        this.viewAccessor = viewAccessor
        callbacks.forEach { it.onViewAccessor(viewAccessor) }
    }
}