package io.androidalatan.bundle.collector.flow.adapter

import io.androidalatan.bundle.assertion.MapIntentData
import io.androidalatan.bundle.collector.assertion.MockBundleCollectorStream
import io.androidalatan.coroutine.test.turbine
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BundleCollectorStreamFlowAdapterKtTest {

    @Test
    fun intentData() {
        val collectorStream = MockBundleCollectorStream()
        collectorStream
            .intentData()
            .turbine { flowTurbine ->
                val item = flowTurbine.awaitItem()
                Assertions.assertEquals(emptyMap<String, Any>(), item.getAll())
                Assertions.assertNull(item.getUriStringOrNull())
                Assertions.assertNull(item.getTypeOrNull())

                val key = "key-1"
                val value = 1
                val uriString = "uristring-1"
                val type = "type-1"
                collectorStream.updateIntent(MapIntentData(mutableMapOf(key to value), uriString = uriString, type = type))

                val item2 = flowTurbine.awaitItem()
                Assertions.assertEquals(1, item2.getAll().size)
                Assertions.assertEquals(value, item2.getIntOrNull(key))
                Assertions.assertEquals(uriString, item2.getUriStringOrNull())
                Assertions.assertEquals(type, item2.getTypeOrNull())

                flowTurbine.cancelAndIgnoreRemainingEvents()
            }
    }

}