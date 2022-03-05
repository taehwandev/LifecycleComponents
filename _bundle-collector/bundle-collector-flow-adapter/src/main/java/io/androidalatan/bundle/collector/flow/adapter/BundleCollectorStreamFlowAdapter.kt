package io.androidalatan.bundle.collector.flow.adapter

import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.bundle.api.IntentData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun BundleCollectorStream.intentData(): Flow<IntentData> {
    return callbackFlow {

        val callback = BundleCollectorStream.Callback {
            trySend(it)
        }
        registerIntentDataCallback(callback)

        awaitClose { unregisterIntentDataCallback(callback) }
    }
}