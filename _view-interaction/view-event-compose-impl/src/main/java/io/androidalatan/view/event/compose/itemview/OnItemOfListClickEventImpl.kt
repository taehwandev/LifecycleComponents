package io.androidalatan.view.event.compose.itemview

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.view.OnItemClick
import io.androidalatan.view.event.api.itemview.OnItemOfListClickEvent

class OnItemOfListClickEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnItemOfListClickEvent {

    private val callbacks = mutableListOf<OnItemOfListClickEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnItemClick>? = null

    override fun registerOnItemClickEvent(callback: OnItemOfListClickEvent.Callback) {
        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnItemClick> { item ->
                callbacks.forEach { it.onItemClick(OnItemOfListClickEvent.ClickedItem(item.index, item.item)) }
            }
            interactionObserver.registerCallback(id, OnItemClick::class.java, false, baseCallback)
            this.baseCallback = baseCallback
        }
        callbacks.add(callback)
    }

    override fun unregisterOnItemClickEvent(callback: OnItemOfListClickEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnItemClick::class.java)
        }
    }
}