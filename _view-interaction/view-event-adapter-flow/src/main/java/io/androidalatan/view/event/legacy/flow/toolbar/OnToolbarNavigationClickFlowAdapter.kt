package io.androidalatan.view.event.legacy.flow.toolbar

import io.androidalatan.view.event.api.toolbar.OnToolbarNavigationClick
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun OnToolbarNavigationClick.onNavigationClickAsFlow(): Flow<Long> {
    return callbackFlow {
        var clickCount = 0L
        val callback = OnToolbarNavigationClick.Callback { trySend(clickCount++) }
        registerOnNavigationClickEvent(callback)

        awaitClose { unregisterOnNavigationClickEvent(callback) }
    }
}