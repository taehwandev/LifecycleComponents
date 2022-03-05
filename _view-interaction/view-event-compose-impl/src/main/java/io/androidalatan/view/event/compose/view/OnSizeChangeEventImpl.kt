package io.androidalatan.view.event.compose.view

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.view.ViewSizeEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent

class OnSizeChangeEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnSizeChangeEvent {
    private val callbacks = mutableListOf<OnSizeChangeEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<ViewSizeEvent>? = null

    override fun registerOnSizeChangeCallback(callback: OnSizeChangeEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<ViewSizeEvent> { viewSize ->
                callbacks.forEach { it.onSizeChange(OnSizeChangeEvent.ViewSize(viewSize.width, viewSize.height)) }
            }
            interactionObserver.registerCallback(id, ViewSizeEvent::class.java, true, baseCallback)
            this.baseCallback = baseCallback
        }
        callbacks.add(callback)
    }

    override fun unregisterOnSizeChangeCallback(callback: OnSizeChangeEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, ViewSizeEvent::class.java)
        }
    }

}