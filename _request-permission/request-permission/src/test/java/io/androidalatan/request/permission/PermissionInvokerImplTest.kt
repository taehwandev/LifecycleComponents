package io.androidalatan.request.permission

import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.os.BuildCompat
import androidx.fragment.app.FragmentActivity
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PermissionInvokerImplTest {

    private val activity = mock<FragmentActivity>()

    private val invoker = PermissionInvokerImpl(LazyProvider(activity))

    @Test
    fun checkPermission() {
        val activityCompat = mockStatic(ActivityCompat::class.java)
        val textUtils = mockStatic(TextUtils::class.java)
        val process = mockStatic(Process::class.java)
        val buildCompat = mockStatic(BuildCompat::class.java)
        whenever(TextUtils.equals(any(), any())).thenReturn(false)
        whenever(ActivityCompat.checkSelfPermission(activity, PERMISSION_1)).thenReturn(PackageManager.PERMISSION_GRANTED)
        whenever(ActivityCompat.checkSelfPermission(activity, PERMISSION_2)).thenReturn(PackageManager.PERMISSION_DENIED)
        Assertions.assertTrue(invoker.checkPermission(PERMISSION_1))
        Assertions.assertFalse(invoker.checkPermission(PERMISSION_2))
        buildCompat.close()
        process.close()
        textUtils.close()
        activityCompat.close()
    }

    @Test
    fun shouldShowPermissionExplanation() {
        val activityCompat = mockStatic(ActivityCompat::class.java)
        whenever(ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_1)).thenReturn(true)
        whenever(ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_2)).thenReturn(false)
        Assertions.assertTrue(invoker.shouldShowPermissionExplanation(PERMISSION_1))
        Assertions.assertFalse(invoker.shouldShowPermissionExplanation(PERMISSION_2))
        activityCompat.close()
    }

    @Test
    fun requestPermission() {
        val activityCompat = mockStatic(ActivityCompat::class.java)
        val requestCode = 1
        val permissions = arrayOf(PERMISSION_1, PERMISSION_2)
        invoker.requestPermission(permissions, requestCode)
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
        activityCompat.close()
    }

    companion object {
        private const val PERMISSION_1 = "permission-1"
        private const val PERMISSION_2 = "permission-2"
    }
}