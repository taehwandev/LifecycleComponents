package io.androidalatan.request.permission.api

interface PermissionExplanation {
    fun show(callback: OnConfirm)

    fun interface OnConfirm {
        fun onConfirm()
    }
}