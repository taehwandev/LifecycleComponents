package io.androidalatan.view.event.legacy.flow.view

import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

fun OnSizeChangeEvent.onSizeChangeAsFlow(): Flow<OnSizeChangeEvent.ViewSize> {
    return callbackFlow {
        val callback = OnSizeChangeEvent.Callback { viewSize ->
            trySend(viewSize)
        }
        registerOnSizeChangeCallback(callback)

        awaitClose {
            unregisterOnSizeChangeCallback(callback)
        }
    }
        .distinctUntilChanged()
}