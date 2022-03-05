package io.androidalatan.request.permission.api

interface PermissionHandler {
    fun onResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    fun request(
        permissions: Array<String>,
        reqCode: Int,
        explanation: ((PermissionExplanationBuilder) -> PermissionExplanation)? = null,
        fail: (Exception) -> Unit = {},
        success: (PermissionResult) -> Unit
    )
}