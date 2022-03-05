package io.androidalatan.request.permission.api

interface PermissionStream {
    fun registerAndRunCallback(callback: Callback)
    fun unregisterCallback(callback: Callback)

    fun interface Callback {
        fun onCallback(handler: PermissionHandler)
    }
}