package io.androidalatan.view.event.legacy.flow.view

import io.androidalatan.view.event.api.view.OnLongClickEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun OnLongClickEvent.onLongClickAsFlow(): Flow<Long> {
    return callbackFlow {
        var clicked = 0L
        val callback = OnLongClickEvent.Callback {
            trySend(clicked++)
        }
        registerOnLongClickEvent(callback)

        awaitClose {
            unregisterOnLongClickEvent(callback)
        }
    }
}