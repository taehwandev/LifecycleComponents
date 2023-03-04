package io.androidalatan.lifecycle.handler.compose.cache

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ComposeCacheProviderTest {
    @Test
    fun `build and property getter`() {
        val composeCacheProvider = composeCacheProvider()
        assertTrue(composeCacheProvider is ComposeCacheProvider)
        val value1 = "hello"
        var testValue by composeCacheProvider.composeCached { value1 }
        assertEquals(value1, testValue)
        assertEquals(value1, composeCacheProvider.composeCache().cached<String>(String::class.qualifiedName))

        val value2 = "world"
        testValue = value2
        assertEquals(value2, testValue)
        assertEquals(value2, composeCacheProvider.composeCache().cached<String>(String::class.qualifiedName))
    }
}