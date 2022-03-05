package io.androidalatan.bundle.collector.api

import io.androidalatan.bundle.api.IntentData

interface BundleCollectorStore {
    fun updateIntent(newIntentData: IntentData)
}