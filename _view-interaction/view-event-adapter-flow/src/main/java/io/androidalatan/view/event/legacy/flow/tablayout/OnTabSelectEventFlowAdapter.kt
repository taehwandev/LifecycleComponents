package io.androidalatan.view.event.legacy.flow.tablayout

import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun OnTabSelectEvent.onTabSelectEventAsFlow(): Flow<OnTabSelectEvent.SelectedTab> {
    return callbackFlow {
        val callback = OnTabSelectEvent.Callback { selectedTab ->
            trySend(selectedTab)
        }
        registerOnTabSelectCallback(callback)
        awaitClose {
            unregisterOnTabSelectCallback(callback)
        }
    }
}

fun OnTabSelectEvent.onIndexTabSelectEventAsFlow(): Flow<Int> {
    return callbackFlow {
        val callback = OnTabSelectEvent.Callback { selectedTab ->
            if (selectedTab.type == OnTabSelectEvent.SelectedEventType.SELECTED) {
                trySend(selectedTab.position)
            }
        }
        registerOnTabSelectCallback(callback)
        awaitClose {
            unregisterOnTabSelectCallback(callback)
        }
    }
}
