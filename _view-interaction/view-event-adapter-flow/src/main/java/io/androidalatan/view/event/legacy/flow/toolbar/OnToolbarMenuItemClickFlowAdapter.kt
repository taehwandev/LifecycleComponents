package io.androidalatan.view.event.legacy.flow.toolbar

import io.androidalatan.view.event.api.toolbar.OnToolbarMenuItemClick
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun OnToolbarMenuItemClick.onMenuItemClickAsFlow(): Flow<Int> {
    return callbackFlow {
        val callback = OnToolbarMenuItemClick.Callback { trySend(it) }
        registerOnMenuItemClickEvent(callback)

        awaitClose { unregisterOnMenuItemClickEvent(callback) }
    }
}