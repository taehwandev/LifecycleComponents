package io.androidalatan.result.handler

import android.content.Intent
import android.os.Bundle
import io.androidalatan.bundle.IntentDataImpl
import io.androidalatan.bundle.api.IntentData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ResultDataTest {

    private lateinit var bundle: Bundle
    private lateinit var bundleData: IntentData

    @BeforeEach
    fun setUp() {
        val intent = Mockito.mock(Intent::class.java)
        bundle = Mockito.mock(Bundle::class.java)
        whenever(intent.extras).thenReturn(bundle)

        bundleData = IntentDataImpl(intent)
    }

    @Test
    fun hasValue() {
        val noKey = "key-1"
        val hasKey = "key-2"
        whenever(bundle.containsKey(noKey)).thenReturn(false)
        whenever(bundle.containsKey(hasKey)).thenReturn(true)

        Assertions.assertFalse(bundleData.hasValue(noKey))
        Mockito.verify(bundle)
            .containsKey(noKey)
        Assertions.assertTrue(bundleData.hasValue(hasKey))
        Mockito.verify(bundle)
            .containsKey(hasKey)
    }

    @Test
    fun getString() {
        val key1 = "key-1"
        val value1 = "value-1"
        val key2 = "key-2"
        val value2 = "value-2"
        whenever(bundle.getString(key1)).thenReturn(value1)
        whenever(bundle.getString(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getStringOrNull(key1))
        Mockito.verify(bundle)
            .getString(key1)
        Assertions.assertEquals(value2, bundleData.getStringOrNull(key2))
        Mockito.verify(bundle)
            .getString(key2)
    }

    @Test
    fun getInt() {
        val key1 = "key-1"
        val value1 = 1
        val key2 = "key-2"
        val value2 = 2
        whenever(bundle.getInt(key1)).thenReturn(value1)
        whenever(bundle.getInt(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getIntOrNull(key1))
        Mockito.verify(bundle)
            .getInt(key1)
        Assertions.assertEquals(value2, bundleData.getIntOrNull(key2))
        Mockito.verify(bundle)
            .getInt(key2)
    }

    @Test
    fun getByte() {
        val key1 = "key-1"
        val value1 = 1.toByte()
        val key2 = "key-2"
        val value2 = 2.toByte()
        whenever(bundle.getByte(key1)).thenReturn(value1)
        whenever(bundle.getByte(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getByteOrNull(key1))
        Mockito.verify(bundle)
            .getByte(key1)
        Assertions.assertEquals(value2, bundleData.getByteOrNull(key2))
        Mockito.verify(bundle)
            .getByte(key2)
    }

    @Test
    fun getLong() {
        val key1 = "key-1"
        val value1 = 1L
        val key2 = "key-2"
        val value2 = 2L
        whenever(bundle.getLong(key1)).thenReturn(value1)
        whenever(bundle.getLong(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getLongOrNull(key1))
        Mockito.verify(bundle)
            .getLong(key1)
        Assertions.assertEquals(value2, bundleData.getLongOrNull(key2))
        Mockito.verify(bundle)
            .getLong(key2)
    }

    @Test
    fun getDouble() {
        val key1 = "key-1"
        val value1 = 1.0
        val key2 = "key-2"
        val value2 = 2.0
        whenever(bundle.getDouble(key1)).thenReturn(value1)
        whenever(bundle.getDouble(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getDoubleOrNull(key1))
        Mockito.verify(bundle)
            .getDouble(key1)
        Assertions.assertEquals(value2, bundleData.getDoubleOrNull(key2))
        Mockito.verify(bundle)
            .getDouble(key2)
    }

    @Test
    fun getFloat() {
        val key1 = "key-1"
        val value1 = 1f
        val key2 = "key-2"
        val value2 = 2f
        whenever(bundle.getFloat(key1)).thenReturn(value1)
        whenever(bundle.getFloat(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getFloatOrNull(key1))
        Mockito.verify(bundle)
            .getFloat(key1)
        Assertions.assertEquals(value2, bundleData.getFloatOrNull(key2))
        Mockito.verify(bundle)
            .getFloat(key2)
    }

    @Test
    fun getBoolean() {
        val key1 = "key-1"
        val value1 = true
        val key2 = "key-2"
        val value2 = false
        whenever(bundle.getBoolean(key1)).thenReturn(value1)
        whenever(bundle.getBoolean(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getBooleanOrNull(key1))
        Mockito.verify(bundle)
            .getBoolean(key1)
        Assertions.assertEquals(value2, bundleData.getBooleanOrNull(key2))
        Mockito.verify(bundle)
            .getBoolean(key2)
    }

    @Test
    fun getStringArray() {
        val key1 = "key-1"
        val value1 = arrayOf("value-1")
        val key2 = "key-2"
        val value2 = arrayOf("value-2")
        whenever(bundle.getStringArray(key1)).thenReturn(value1)
        whenever(bundle.getStringArray(key2)).thenReturn(value2)

        Assertions.assertEquals(value1, bundleData.getStringArray(key1))
        Mockito.verify(bundle)
            .getStringArray(key1)
        Assertions.assertEquals(value2, bundleData.getStringArray(key2))
        Mockito.verify(bundle)
            .getStringArray(key2)
    }
}