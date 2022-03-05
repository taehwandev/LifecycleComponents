package io.androidalatan.view.event.compose.compoundbutton

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.compoundbutton.OnCheckedChanged
import io.androidalatan.view.event.api.compoundbutton.OnCheckedChangeEvent

class OnCheckedChangeEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnCheckedChangeEvent {
    private val callbacks = mutableListOf<OnCheckedChangeEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnCheckedChanged>? = null
    override fun registerOnCheckChangeCallback(callback: OnCheckedChangeEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnCheckedChanged> { onChecked ->
                callbacks.forEach { it.onCheckChange(onChecked.checked) }
            }
            interactionObserver.registerCallback(
                id,
                OnCheckedChanged::class.java,
                true,
                baseCallback
            )
            this.baseCallback = baseCallback
        }

        callbacks.add(callback)
    }

    override fun unregisterOnCheckChangeCallback(callback: OnCheckedChangeEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnCheckedChanged::class.java)
        }
    }
}