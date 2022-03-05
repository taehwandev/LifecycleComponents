package io.androidalatan.bundle.collector

import androidx.annotation.VisibleForTesting
import io.androidalatan.bundle.collector.api.BundleCollectorStore
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.bundle.api.IntentData

class BundleCollectorStreamImpl(
    defaultBundleData: IntentData = IntentData.EMPTY
) : BundleCollectorStream,
    BundleCollectorStore {

    @VisibleForTesting internal var intentData = defaultBundleData

    @VisibleForTesting internal val callbacks = mutableListOf<BundleCollectorStream.Callback>()

    private val lock = Any()

    override fun updateIntent(newIntentData: IntentData) {
        synchronized(lock) {
            this.intentData = newIntentData
            callbacks.forEach { it.onEvent(newIntentData) }
        }
    }

    override fun registerIntentDataCallback(callback: BundleCollectorStream.Callback) {
        synchronized(lock) {
            callbacks.add(callback)
            callback.onEvent(intentData)
        }
    }

    override fun unregisterIntentDataCallback(callback: BundleCollectorStream.Callback) {
        synchronized(lock) {
            callbacks.remove(callback)
        }
    }

    fun getCurrentBundle(): BundleData = intentData

}