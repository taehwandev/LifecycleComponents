package io.androidalatan.request.permission

import android.Manifest
import android.content.pm.PackageManager
import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionExplanationBuilderFactory
import io.androidalatan.request.permission.api.PermissionResult
import io.androidalatan.request.permission.api.PermissionStream
import io.androidalatan.request.permission.api.exception.PermissionGrantException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

class PermissionStreamImplTest {

    private val permissionInvoker = Mockito.mock(PermissionInvoker::class.java)
    private val permissionExplanationBuilderFactory = Mockito.mock(
        PermissionExplanationBuilderFactory::class.java
    )
    private val stream = PermissionStreamImpl(permissionInvoker, permissionExplanationBuilderFactory)

    @Test
    fun `request already granted`() {
        whenever(permissionInvoker.checkPermission(any())).thenReturn(true)

        val reqCode = 1231
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        var success = false
        var result: PermissionResult? = null
        val callback = PermissionStream.Callback {
            it.request(arrayOf(locationPermission), reqCode, null, {}, { success = true; result = it })
        }
        stream.registerAndRunCallback(callback)
        assertTrue(success)
        assertEquals(result, PermissionResult(reqCode, listOf(locationPermission to true)))
        stream.unregisterCallback(callback)
    }

    @Test
    fun `requestPermissions no explanation and granted all`() {
        // happy path. user grants all permission once first time

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(false)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        var success = false
        var result: PermissionResult? = null
        val callback = PermissionStream.Callback {
            it.request(arrayOf(permission1, permission2), reqCode, null, {}, { success = true; result = it })
        }
        stream.registerAndRunCallback(callback)

        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)
        assertFalse(success)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED)
        )
        assertTrue(success)
        assertEquals(result, PermissionResult(reqCode, listOf(permission1 to true, permission2 to true)))
        stream.unregisterCallback(callback)

    }

    @Test
    fun `requestPermissions no explanation and granted partial with explanation`() {
        // user grants permissions partially first time

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(false)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        var success = false
        var result: PermissionResult? = null
        val callback = PermissionStream.Callback {
            it.request(arrayOf(permission1, permission2), reqCode, { it.build() }, { }, { success = true; result = it })
        }
        stream.registerAndRunCallback(callback)

        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)
        assertFalse(success)

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(true)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_GRANTED)
        )

        assertTrue(success)
        assertNotNull(result)
        assertEquals(result, PermissionResult(reqCode, listOf(permission1 to false, permission2 to true)))
        stream.unregisterCallback(callback)

    }

    @Test
    fun `requestPermissions no explanation and granted partial with no explanation`() {
        // user grants nothing first time

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(false)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        var success = false
        var result: PermissionResult? = null
        val callback = PermissionStream.Callback {
            it.request(arrayOf(permission1, permission2), reqCode, { it.build() }, { }, { success = true; result = it })
        }
        stream.registerAndRunCallback(callback)

        assertFalse(success)
        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)
        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(true)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_GRANTED)
        )
        assertTrue(success)
        assertNotNull(result)
        assertEquals(result, PermissionResult(reqCode, listOf(permission1 to false, permission2 to true)))
        stream.unregisterCallback(callback)

    }

    @Test
    fun `requestPermissions explanation and granted all`() {
        // no granted before. show our popup and system permission popup. then user grants all

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(true)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val explanationBuilder = Mockito.mock(PermissionExplanationBuilder::class.java)
        whenever(permissionExplanationBuilderFactory.explanationBuilder()).thenReturn(
            explanationBuilder
        )
        val explanation = Mockito.mock(PermissionExplanation::class.java)
        whenever(explanationBuilder.build()).thenReturn(explanation)

        var success = false
        val callback = PermissionStream.Callback {
            it.request(arrayOf(permission1, permission2), reqCode, { it.build() }, {}, { success = true })
        }
        stream.registerAndRunCallback(callback)

        val argumentCaptor = argumentCaptor<PermissionExplanation.OnConfirm>()
        Mockito
            .verify(explanation)
            .show(argumentCaptor.capture())
        argumentCaptor.firstValue.onConfirm()

        Mockito.verify(permissionExplanationBuilderFactory)
            .explanationBuilder()
        Mockito.verify(explanationBuilder)
            .build()
        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED)
        )
        assertTrue(success)

        stream.unregisterCallback(callback)

    }

    @Test
    fun `requestPermissions explanation and granted partially but still system requires explanation`() {
        // no granted before. show our popup and system permission popup. then user grants partial without forever-deny

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(true)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val explanationBuilder = Mockito.mock(PermissionExplanationBuilder::class.java)
        whenever(permissionExplanationBuilderFactory.explanationBuilder()).thenReturn(
            explanationBuilder
        )
        val explanation = Mockito.mock(PermissionExplanation::class.java)
        whenever(explanationBuilder.build()).thenReturn(explanation)

        var success = false
        var result: PermissionResult? = null
        val callback = PermissionStream.Callback {
            it.request(
                arrayOf(permission1, permission2),
                reqCode,
                { builder -> builder.build() },
                { },
                { success = true; result = it })
        }
        stream.registerAndRunCallback(callback)

        val argumentCaptor = argumentCaptor<PermissionExplanation.OnConfirm>()
        Mockito.verify(explanation)
            .show(argumentCaptor.capture())
        argumentCaptor.firstValue.onConfirm()

        Mockito.verify(permissionExplanationBuilderFactory)
            .explanationBuilder()
        Mockito.verify(explanationBuilder)
            .build()
        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_DENIED)
        )

        assertTrue(success)
        assertEquals(result, PermissionResult(reqCode, listOf(permission1 to true, permission2 to false)))
        stream.unregisterCallback(callback)

    }

    @Test
    fun `requestPermissions explanation with forever-deny`() {
        // no granted before. show our popup and system permission popup. then user grants partial without forever-deny

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(true)
        val reqCode = 3123
        val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
        val permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val explanationBuilder = Mockito.mock(PermissionExplanationBuilder::class.java)
        whenever(permissionExplanationBuilderFactory.explanationBuilder()).thenReturn(
            explanationBuilder
        )
        val explanation = Mockito.mock(PermissionExplanation::class.java)
        whenever(explanationBuilder.build()).thenReturn(explanation)

        var fail = false
        var exception: Exception? = null
        val callback = PermissionStream.Callback {
            it.request(arrayOf(permission1, permission2), reqCode, { it.build() }, { fail = true; exception = it }, { })
        }
        stream.registerAndRunCallback(callback)

        val argumentCaptor = argumentCaptor<PermissionExplanation.OnConfirm>()
        Mockito.verify(explanation)
            .show(argumentCaptor.capture())
        argumentCaptor.firstValue.onConfirm()

        Mockito.verify(permissionExplanationBuilderFactory)
            .explanationBuilder()
        Mockito.verify(explanationBuilder)
            .build()
        Mockito.verify(permissionInvoker)
            .requestPermission(arrayOf(permission1, permission2), reqCode)

        whenever(permissionInvoker.shouldShowPermissionExplanation(any())).thenReturn(false)

        stream.onResult(
            requestCode = reqCode,
            permissions = arrayOf(permission1, permission2),
            grantResults = intArrayOf(PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_GRANTED)
        )

        assertTrue(fail)
        assertNotNull(exception)
        assertTrue(exception is PermissionGrantException)

        stream.unregisterCallback(callback)
    }
}