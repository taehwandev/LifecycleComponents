package io.androidalatan.request.permission

import android.content.pm.PackageManager
import android.os.Process
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [ActivityCompat::class, Process::class])
class PermissionInvokerImplTest {

    private val activity = mock<FragmentActivity>()

    private val invoker = PermissionInvokerImpl(LazyProvider(activity))

    @Test
    fun checkPermission() {
        PowerMockito.mockStatic(ActivityCompat::class.java)
        PowerMockito.mockStatic(Process::class.java)
        whenever(ActivityCompat.checkSelfPermission(activity, PERMISSION_1)).thenReturn(PackageManager.PERMISSION_GRANTED)
        whenever(ActivityCompat.checkSelfPermission(activity, PERMISSION_2)).thenReturn(PackageManager.PERMISSION_DENIED)
        Assertions.assertTrue(invoker.checkPermission(PERMISSION_1))
        Assertions.assertFalse(invoker.checkPermission(PERMISSION_2))
    }

    @Test
    fun shouldShowPermissionExplanation() {
        PowerMockito.mockStatic(ActivityCompat::class.java)
        whenever(ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_1)).thenReturn(true)
        whenever(ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_2)).thenReturn(false)
        Assertions.assertTrue(invoker.shouldShowPermissionExplanation(PERMISSION_1))
        Assertions.assertFalse(invoker.shouldShowPermissionExplanation(PERMISSION_2))
    }

    @Test
    fun requestPermission() {
        PowerMockito.mockStatic(ActivityCompat::class.java)
        val requestCode = 1
        val permissions = arrayOf(PERMISSION_1, PERMISSION_2)
        invoker.requestPermission(permissions, requestCode)
        PowerMockito.verifyStatic(ActivityCompat::class.java)
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    companion object {
        private const val PERMISSION_1 = "permission-1"
        private const val PERMISSION_2 = "permission-2"
    }
}