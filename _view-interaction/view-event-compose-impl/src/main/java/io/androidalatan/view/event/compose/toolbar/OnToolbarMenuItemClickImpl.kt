package io.androidalatan.view.event.compose.toolbar

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.toolbar.OnToolbarMenuItemClick

private typealias OnMenuItemClickEvent = io.androidalatan.component.view.compose.api.toolbar.OnToolbarMenuItemClick

class OnToolbarMenuItemClickImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnToolbarMenuItemClick {
    private val callbacks = mutableListOf<OnToolbarMenuItemClick.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnMenuItemClickEvent>? = null

    override fun registerOnMenuItemClickEvent(callback: OnToolbarMenuItemClick.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnMenuItemClickEvent> { event ->
                callbacks.forEach { it.onNavigationMenuItemClick(event.id) }
            }
            interactionObserver.registerCallback(id, OnMenuItemClickEvent::class.java, false, baseCallback)
            this.baseCallback = baseCallback
        }

        callbacks.add(callback)
    }

    override fun unregisterOnMenuItemClickEvent(callback: OnToolbarMenuItemClick.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnMenuItemClickEvent::class.java)
        }
    }
}