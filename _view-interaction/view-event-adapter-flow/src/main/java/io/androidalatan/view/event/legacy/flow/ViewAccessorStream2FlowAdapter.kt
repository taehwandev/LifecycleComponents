package io.androidalatan.view.event.legacy.flow

import io.androidalatan.view.event.api.ViewAccessor
import io.androidalatan.view.event.api.ViewAccessorStream
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ViewAccessorStream.asFlow(): Flow<ViewAccessor> {
    return callbackFlow {
        val callback = ViewAccessorStream.Callback {
            trySend(it)
        }
        registerCallback(callback)

        awaitClose { unregisterCallback(callback) }
    }
}