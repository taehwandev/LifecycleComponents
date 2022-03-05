package io.androidalatan.view.event.compose.toolbar

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.toolbar.OnToolbarNavigationClick

private typealias OnClickEvent = io.androidalatan.component.view.compose.api.toolbar.OnToolbarNavigationClick

class OnToolbarNavigationClickImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnToolbarNavigationClick {
    private val callbacks = mutableListOf<OnToolbarNavigationClick.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnClickEvent>? = null

    override fun registerOnNavigationClickEvent(callback: OnToolbarNavigationClick.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback =
                ComposeViewInteractionObserver.Callback<OnClickEvent> {
                    callbacks.forEach { it.onToolbarNavigationClick() }
                }
            interactionObserver.registerCallback(id, OnClickEvent::class.java, false, baseCallback)
            this.baseCallback = baseCallback
        }

        callbacks.add(callback)
    }

    override fun unregisterOnNavigationClickEvent(callback: OnToolbarNavigationClick.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnClickEvent::class.java)
        }
    }
}