package io.androidalatan.request.permission

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import io.androidalatan.lifecycle.lazyprovider.LazyProvider

interface PermissionInvoker {
    fun checkPermission(permission: String): Boolean
    fun shouldShowPermissionExplanation(permission: String): Boolean
    fun requestPermission(permissions: Array<String>, reqCode: Int)
}

class PermissionInvokerImpl(private val lazyActivity: LazyProvider<FragmentActivity>) : PermissionInvoker {
    override fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(lazyActivity.value, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun shouldShowPermissionExplanation(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(lazyActivity.value, permission)
    }

    override fun requestPermission(permissions: Array<String>, reqCode: Int) {
        ActivityCompat.requestPermissions(lazyActivity.value, permissions, reqCode)
    }
}