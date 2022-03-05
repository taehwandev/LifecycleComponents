package io.androidalatan.view.event.legacy.view

import android.view.View
import io.androidalatan.view.event.api.view.OnClickEvent

class OnClickEventImpl(private val view: View) : OnClickEvent {

    private val callbacks = mutableListOf<OnClickEvent.Callback>()

    override fun registerOnClickEvent(callback: OnClickEvent.Callback) {
        if (callbacks.isEmpty()) {
            view.setOnClickListener {
                callbacks.forEach(OnClickEvent.Callback::onClick)
            }
        }
        callbacks.add(callback)
    }

    override fun unregisterOnClickEvent(callback: OnClickEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            view.setOnClickListener(null)
        }
    }
}