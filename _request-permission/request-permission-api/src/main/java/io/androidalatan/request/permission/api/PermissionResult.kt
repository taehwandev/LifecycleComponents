package io.androidalatan.request.permission.api

data class PermissionResult(
    val requestCode: Int,
    val result: List<Pair<String, Boolean>>
)