package io.androidalatan.request.permission.api.exception

import io.androidalatan.request.permission.api.PermissionResult

class PermissionGrantException(val ungratned: List<String>, val origin: PermissionResult) :
    RuntimeException("$ungratned will never be granted")