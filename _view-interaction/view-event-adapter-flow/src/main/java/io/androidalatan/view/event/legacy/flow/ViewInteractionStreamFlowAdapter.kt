package io.androidalatan.view.event.legacy.flow

import io.androidalatan.view.event.api.ViewInteractionController
import io.androidalatan.view.event.api.ViewInteractionStream
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ViewInteractionStream.asFlow(): Flow<ViewInteractionController> {
    return callbackFlow {
        val callback = ViewInteractionStream.Callback {
            trySend(it)
        }
        registerCallback(callback)

        awaitClose { unregisterCallback(callback) }
    }
}