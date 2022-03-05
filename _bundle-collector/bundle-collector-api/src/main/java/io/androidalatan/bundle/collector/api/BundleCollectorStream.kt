package io.androidalatan.bundle.collector.api

import io.androidalatan.bundle.api.IntentData

interface BundleCollectorStream {

    fun registerIntentDataCallback(callback: Callback)
    fun unregisterIntentDataCallback(callback: Callback)

    fun interface Callback {
        fun onEvent(t: IntentData)
    }
}