package io.androidalatan.lifecycle.handler.compose.cache

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ComposeCacheImplTest {
    private val cache = ComposeCacheImpl()

    @Test
    fun cached() {
        val obj = "Hello World~!"
        cache.save(obj)
        assertEquals(obj, cache.cached<String>(String::class.qualifiedName))
    }

    @Test
    fun cachedValues() {
        val obj = "Hello World~!"
        cache.save(obj)
        assertEquals(1, cache.cachedValues().size)
        assertTrue(
            cache.cachedValues()
                .contains(obj)
        )
        cache.clear()
        assertEquals(0, cache.cachedValues().size)
    }
}