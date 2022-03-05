package io.androidalatan.component.view.compose

import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent
import io.androidalatan.component.view.compose.api.compoundbutton.OnCheckedChanged
import io.androidalatan.component.view.compose.api.holder.Holdable
import io.androidalatan.component.view.compose.api.holder.ViewEventHolder
import io.androidalatan.component.view.compose.api.tabs.OnTabSelected
import io.androidalatan.component.view.compose.api.textfield.OnFocusChanged
import io.androidalatan.component.view.compose.api.textfield.OnTextChanged
import io.androidalatan.component.view.compose.model.ViewEventCallback

class ComposeViewInteractionTriggerImpl : ComposeViewInteractionTrigger, ComposeViewInteractionObserver {

    private val cacheableEvent by lazy { hashMapOf<Int, ViewEventHolder>() }

    private var callbacks = mutableListOf<ViewEventCallback<ViewEvent>>()

    override fun triggerEvent(id: Int, viewEvent: ViewEvent) {
        if (viewEvent is Holdable) {
            cacheableEvent[id] = ViewEventHolder(viewEvent)
        }

        synchronized(this) {
            executeCallback(id, viewEvent)
        }
    }

    private fun executeCallback(id: Int, viewEvent: ViewEvent) {
        callbacks.filter { it.id == id && it.event == viewEvent.javaClass }
            .forEach {
                it.callback.onEvent(viewEvent)
            }
    }

    override fun <T : ViewEvent> registerCallback(
        id: Int,
        event: Class<T>,
        startWithCachedItem: Boolean,
        callback: ComposeViewInteractionObserver.Callback<T>
    ) {
        synchronized(this) {
            callbacks.add(ViewEventCallback(id, event, callback) as ViewEventCallback<ViewEvent>)

            if (cacheableEvent[id] == null && Holdable::class.java.isAssignableFrom(event)) {
                cacheableEvent[id] = ViewEventHolder(createDefaultEvent(event) as Holdable)
            }

            val viewEvent: Holdable? = cacheableEvent[id]?.viewEvent
            if (viewEvent != null && viewEvent.javaClass == event && startWithCachedItem) {
                callback.onEvent(viewEvent as T)
            }
        }
    }

    private fun <T : ViewEvent> createDefaultEvent(event: Class<T>): Holdable {
        return when (event) {
            OnFocusChanged::class.java -> OnFocusChanged(false)
            OnTabSelected::class.java -> OnTabSelected(0)
            OnCheckedChanged::class.java -> OnCheckedChanged(false)
            OnTextChanged::class.java -> OnTextChanged("")
            else -> throw IllegalArgumentException("${event.name} should be inherited from Holdable")
        }
    }

    override fun <T : ViewEvent> unregisterCallback(id: Int, event: Class<T>) {
        synchronized(this) {
            callbacks.filter { it.id == id && it.event.javaClass == event }
                .forEach {
                    callbacks.remove(it)
                }
        }
    }
}