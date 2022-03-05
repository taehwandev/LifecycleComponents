package io.androidalatan.bundle.collector.assertion

import io.androidalatan.bundle.collector.api.BundleCollectorStore
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.bundle.api.IntentData

class MockBundleCollectorStream : BundleCollectorStream, BundleCollectorStore {
    private var latestBundle: IntentData = IntentData.EMPTY

    private val callbacks = mutableListOf<BundleCollectorStream.Callback>()

    override fun registerIntentDataCallback(callback: BundleCollectorStream.Callback) {
        callbacks.add(callback)
        callback.onEvent(latestBundle)
    }

    override fun unregisterIntentDataCallback(callback: BundleCollectorStream.Callback) {
        callbacks.remove(callback)
    }

    override fun updateIntent(newIntentData: IntentData) {
        callbacks.forEach { it.onEvent(newIntentData) }
    }

    fun reset() {
        callbacks.clear()
        latestBundle = IntentData.EMPTY
    }
}