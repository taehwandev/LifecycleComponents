package io.androidalatan.result.handler

import io.androidalatan.bundle.assertion.MapIntentData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResultDataProviderImplTest {

    private val resultData = MapIntentData(mutableMapOf())
    private val provider = ResultDataProviderImpl(resultData)

    @Test
    fun hasValue() {
        val key1 = "key1"
        val value1 = "value1"
        val key2 = "key2"
        val value2 = "value2"
        resultData.newMapData(mapOf(key1 to value1))

        Assertions.assertTrue(provider.hasValue(key1))
        Assertions.assertFalse(provider.hasValue(key2))
    }

    @Test
    fun getString() {
        val key1 = "key1"
        val value1 = "value1"
        val key2 = "key2"
        val value2 = "value2"
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getStringOrNull(key1))
        Assertions.assertEquals(value2, resultData.getStringOrNull(key2))
    }

    @Test
    fun getInt() {
        val key1 = "key1"
        val value1 = 1
        val key2 = "key2"
        val value2 = 2
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getIntOrNull(key1))
        Assertions.assertEquals(value2, resultData.getIntOrNull(key2))
    }

    @Test
    fun getByte() {
        val key1 = "key1"
        val value1 = 1.toByte()
        val key2 = "key2"
        val value2 = 2.toByte()
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getByteOrNull(key1))
        Assertions.assertEquals(value2, resultData.getByteOrNull(key2))
    }

    @Test
    fun getLong() {
        val key1 = "key1"
        val value1 = 1L
        val key2 = "key2"
        val value2 = 2L
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getLongOrNull(key1))
        Assertions.assertEquals(value2, resultData.getLongOrNull(key2))
    }

    @Test
    fun getDouble() {
        val key1 = "key1"
        val value1 = 1.0
        val key2 = "key2"
        val value2 = 2.0
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getDoubleOrNull(key1))
        Assertions.assertEquals(value2, resultData.getDoubleOrNull(key2))
    }

    @Test
    fun getFloat() {
        val key1 = "key1"
        val value1 = 1f
        val key2 = "key2"
        val value2 = 2f
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getFloatOrNull(key1))
        Assertions.assertEquals(value2, resultData.getFloatOrNull(key2))
    }

    @Test
    fun getBoolean() {
        val key1 = "key1"
        val value1 = true
        val key2 = "key2"
        val value2 = false
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getBooleanOrNull(key1))
        Assertions.assertEquals(value2, resultData.getBooleanOrNull(key2))
    }

    @Test
    fun getStringArray() {
        val key1 = "key1"
        val value1 = arrayOf("1")
        val key2 = "key2"
        val value2 = arrayOf("2")
        resultData.newMapData(mapOf(key1 to value1, key2 to value2))

        Assertions.assertEquals(value1, resultData.getStringArray(key1))
        Assertions.assertEquals(value2, resultData.getStringArray(key2))
    }
}