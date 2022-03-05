package io.androidalatan.view.event.compose.view

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.view.OnClick
import io.androidalatan.view.event.api.view.OnClickEvent

class OnClickEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnClickEvent {

    private val callbacks = mutableListOf<OnClickEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnClick>? = null

    override fun registerOnClickEvent(callback: OnClickEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnClick> {
                callbacks.forEach { it.onClick() }
            }
            interactionObserver.registerCallback(id, OnClick::class.java, false, baseCallback)
            this.baseCallback = baseCallback
        }
        callbacks.add(callback)
    }

    override fun unregisterOnClickEvent(callback: OnClickEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnClick::class.java)
        }
    }
}