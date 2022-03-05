package io.androidalatan.view.event.compose.textview

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.textfield.OnTextChanged
import io.androidalatan.view.event.api.textview.OnTextChangeEvent

class OnTextChangeEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnTextChangeEvent {

    private val callbacks = mutableListOf<OnTextChangeEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnTextChanged>? = null

    override fun registerOnTextChangeEvent(callback: OnTextChangeEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnTextChanged> { textChange ->
                callbacks.forEach { it.onTextChange(OnTextChangeEvent.TextChangeInfo(text = textChange.newText)) }
            }
            interactionObserver.registerCallback(id, OnTextChanged::class.java, true, baseCallback)
            this.baseCallback = baseCallback
        }

        callbacks.add(callback)
    }

    override fun unregisterOnTextChangeEvent(callback: OnTextChangeEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnTextChanged::class.java)
        }
    }
}