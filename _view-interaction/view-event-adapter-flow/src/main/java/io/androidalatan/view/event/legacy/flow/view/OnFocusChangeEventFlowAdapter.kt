package io.androidalatan.view.event.legacy.flow.view

import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

fun OnFocusChangeEvent.onFocusChangeAsFlow(): Flow<Boolean> {
    return callbackFlow {
        val callback = OnFocusChangeEvent.Callback { isFocused ->
            trySend(isFocused)
        }
        registerOnFocusChangeEvent(callback)

        awaitClose {
            unregisterOnFocusChangeEvent(callback)
        }
    }
        .distinctUntilChanged()
}