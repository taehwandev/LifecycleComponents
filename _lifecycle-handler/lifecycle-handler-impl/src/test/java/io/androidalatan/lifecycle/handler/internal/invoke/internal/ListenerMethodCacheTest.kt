package io.androidalatan.lifecycle.handler.internal.invoke.internal

import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.internal.invoke.MockSyncLifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import io.androidalatan.lifecycle.handler.internal.ListenerMethodCache
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ListenerMethodCacheTest {

    private val cache = ListenerMethodCache()

    @Test
    fun add() {
        Assertions.assertEquals(0, cache.invokerInfo.size)
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener.javaClass.declaredMethods[0])
        cache.add(lifecycleListener::class, hashSetOf(methodInfo))

        Assertions.assertEquals(1, cache.invokerInfo.size)
        Assertions.assertEquals(methodInfo, cache[lifecycleListener::class].first())
    }

    @Test
    fun contains() {
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener.javaClass.declaredMethods[0])
        Assertions.assertFalse(lifecycleListener::class in cache)

        cache.add(lifecycleListener::class, hashSetOf(methodInfo))
        Assertions.assertTrue(lifecycleListener::class in cache)

    }

    @Test
    fun get() {
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener.javaClass.declaredMethods[0])

        Assertions.assertEquals(0, cache[lifecycleListener::class].size)

        cache.add(lifecycleListener::class, hashSetOf(methodInfo))
        Assertions.assertEquals(methodInfo, cache[lifecycleListener::class].first())

    }
}