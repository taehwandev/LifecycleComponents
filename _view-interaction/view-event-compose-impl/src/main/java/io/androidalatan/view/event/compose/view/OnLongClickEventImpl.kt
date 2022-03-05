package io.androidalatan.view.event.compose.view

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.view.OnLongClick
import io.androidalatan.view.event.api.view.OnLongClickEvent

class OnLongClickEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnLongClickEvent {
    private val callbacks = mutableListOf<OnLongClickEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnLongClick>? = null
    override fun registerOnLongClickEvent(callback: OnLongClickEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnLongClick> {
                callbacks.forEach { it.onLongClick() }
            }
            interactionObserver.registerCallback(id, OnLongClick::class.java, false, baseCallback)

            this.baseCallback = baseCallback
        }
        callbacks.add(callback)

    }

    override fun unregisterOnLongClickEvent(callback: OnLongClickEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnLongClick::class.java)
        }
    }
}