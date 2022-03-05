package io.androidalatan.backkey.flow.handler

import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.atomic.AtomicLong

fun BackKeyHandlerStream.onBackPressedAsFlow(): Flow<Long> = callbackFlow {

    val count = AtomicLong(0)
    val callback = BackKeyHandlerStream.Callback {
        trySend(count.getAndIncrement())
    }
    registerCallback(callback)

    awaitClose {
        unregisterCallback(callback)
    }

}