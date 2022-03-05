package io.androidalatan.request.permission

import io.androidalatan.request.permission.api.PermissionExplanationBuilderFactory
import io.androidalatan.request.permission.api.PermissionHandler
import io.androidalatan.request.permission.api.PermissionStream

class PermissionStreamImpl(
    permissionInvoker: PermissionInvoker,
    permissionExplanationBuilderFactory: PermissionExplanationBuilderFactory
) : PermissionStream {

    private var callbacks = mutableListOf<PermissionStream.Callback>()

    private val handler: PermissionHandler by lazy {
        PermissionHandlerImpl(permissionInvoker, permissionExplanationBuilderFactory)
    }

    override fun registerAndRunCallback(callback: PermissionStream.Callback) {
        synchronized(this) {
            callbacks.add(callback)
            handler.let { handler -> callback.onCallback(handler) }
        }
    }

    override fun unregisterCallback(callback: PermissionStream.Callback) {
        synchronized(this) {
            callbacks.remove(callback)
        }
    }

    fun onResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        handler.onResult(requestCode = requestCode, permissions = permissions, grantResults = grantResults)
    }
}