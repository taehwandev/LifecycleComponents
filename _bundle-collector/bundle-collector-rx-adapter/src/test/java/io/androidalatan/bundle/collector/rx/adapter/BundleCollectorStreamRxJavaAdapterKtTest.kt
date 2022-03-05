package io.androidalatan.bundle.collector.rx.adapter

import io.androidalatan.bundle.assertion.MapIntentData
import io.androidalatan.bundle.collector.assertion.MockBundleCollectorStream
import org.junit.jupiter.api.Test

class BundleCollectorStreamRxJavaAdapterKtTest {

    @Test
    fun intentData() {
        val stream = MockBundleCollectorStream()

        val testObserver = stream.intentData()
            .test()
            .assertNoErrors()
            .assertNotComplete()
            .assertValueCount(1)
            .assertValue { intentDAta ->
                intentDAta.getAll()
                    .isEmpty() &&
                        intentDAta.getTypeOrNull() == null &&
                        intentDAta.getUriStringOrNull() == null
            }

        val key = "key-1"
        val value = 1
        val uriString = "uristring-1"
        val type = "type-1"
        stream.updateIntent(MapIntentData(mutableMapOf(key to value), uriString = uriString, type = type))

        testObserver.assertValueCount(2)
            .assertValueAt(1) { intentDAta ->

                val bundles = intentDAta.getAll()
                bundles.size == 1 &&
                        intentDAta.getIntOrNull(key) == value &&
                        intentDAta.getTypeOrNull() == type &&
                        intentDAta.getUriStringOrNull() == uriString
            }
            .assertNoErrors()
            .assertNotComplete()
            .dispose()
    }
}