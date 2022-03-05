package io.androidalatan.view.event.compose.view

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.textfield.OnFocusChanged
import io.androidalatan.view.event.api.view.OnFocusChangeEvent

class OnFocusChangeEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnFocusChangeEvent {

    private val callbacks = mutableListOf<OnFocusChangeEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnFocusChanged>? = null

    override fun registerOnFocusChangeEvent(callback: OnFocusChangeEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnFocusChanged> { focused ->
                callbacks.forEach { it.onFocusChange(focused.focused) }
            }
            interactionObserver.registerCallback(id, OnFocusChanged::class.java, true, baseCallback)
            this.baseCallback = baseCallback
        }

        callbacks.add(callback)
    }

    override fun unregisterOnFocusChangeEvent(callback: OnFocusChangeEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnFocusChanged::class.java)
        }
    }
}