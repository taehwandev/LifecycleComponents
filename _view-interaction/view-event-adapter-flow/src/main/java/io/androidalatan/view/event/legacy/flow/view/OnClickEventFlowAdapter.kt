package io.androidalatan.view.event.legacy.flow.view

import io.androidalatan.view.event.api.view.OnClickEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun OnClickEvent.onClickAsFlow(): Flow<Long> {
    return callbackFlow {
        var clicked = 0L
        val callback = OnClickEvent.Callback {
            trySend(clicked++)
        }
        registerOnClickEvent(callback)

        awaitClose {
            unregisterOnClickEvent(callback)
        }
    }
}