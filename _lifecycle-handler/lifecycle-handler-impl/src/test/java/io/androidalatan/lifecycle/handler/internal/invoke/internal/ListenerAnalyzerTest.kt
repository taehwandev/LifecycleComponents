package io.androidalatan.lifecycle.handler.internal.invoke.internal

import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.internal.invoke.MockSyncLifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import io.androidalatan.lifecycle.handler.internal.ListenerAnalyzer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ListenerAnalyzerTest {

    private val analyzer = ListenerAnalyzer()

    @Test
    fun analyze() {
        val lifecycleListener = MockSyncLifecycleListener()
        val methods = analyzer.analyze(lifecycleListener)

        Assertions.assertEquals(1, methods.size)
        Assertions.assertEquals(1, analyzer.methodCache[lifecycleListener::class].size)
        Assertions.assertEquals(MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == "execute" }),
                                analyzer.methodCache[lifecycleListener::class].first())
    }
}