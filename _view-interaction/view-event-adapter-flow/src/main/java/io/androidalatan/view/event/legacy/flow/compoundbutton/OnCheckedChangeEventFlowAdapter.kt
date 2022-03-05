package io.androidalatan.view.event.legacy.flow.compoundbutton

import io.androidalatan.view.event.api.compoundbutton.OnCheckedChangeEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

fun OnCheckedChangeEvent.onCheckedChangeAsFlow(ignoreCurrentStatus: Boolean): Flow<Boolean> {

    return callbackFlow<Boolean> {
        val callback = OnCheckedChangeEvent.Callback {
            trySend(it)
        }
        registerOnCheckChangeCallback(callback)

        awaitClose { unregisterOnCheckChangeCallback(callback) }
    }
        .drop(if (ignoreCurrentStatus) 1 else 0)
        .distinctUntilChanged()

}