package io.androidalatan.bundle.collector

import io.androidalatan.bundle.api.IntentData
import io.androidalatan.bundle.assertion.MapIntentData
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BundleCollectorStreamImplTest {

    private val stream = BundleCollectorStreamImpl()

    @Test
    fun `registerIntentDataCallback - updateIntent - unregisterIntentDataCallback`() {
        var eventCount = 0
        val callback = BundleCollectorStream.Callback { eventCount++ }
        stream.registerIntentDataCallback(callback)
        Assertions.assertEquals(eventCount, 1)
        Assertions.assertEquals(stream.callbacks.size, 1)
        Assertions.assertEquals(stream.intentData, IntentData.EMPTY)

        val newIntentData = MapIntentData(mutableMapOf())
        stream.updateIntent(newIntentData)
        Assertions.assertEquals(eventCount, 2)
        Assertions.assertEquals(stream.intentData, newIntentData)

        stream.unregisterIntentDataCallback(callback)
        Assertions.assertEquals(stream.callbacks.size, 0)

        stream.updateIntent(MapIntentData(mutableMapOf()))
        Assertions.assertEquals(eventCount, 2)

    }
}