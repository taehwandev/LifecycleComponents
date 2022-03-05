package io.androidalatan.component.view.compose.api

import androidx.annotation.IdRes

interface ComposeViewInteractionTrigger {
    fun triggerEvent(@IdRes id: Int, viewEvent: ViewEvent)
}

interface ComposeViewInteractionObserver {
    fun <T : ViewEvent> registerCallback(
        @IdRes id: Int,
        event: Class<T>,
        startWithCachedItem: Boolean,
        callback: Callback<T>
    )

    fun <T : ViewEvent> unregisterCallback(id: Int, event: Class<T>)

    fun interface Callback<T : ViewEvent> {
        fun onEvent(event: T)
    }
}