package io.androidalatan.request.permission.assertions

import io.androidalatan.request.permission.api.PermissionExplanation

class MockPermissionExplanation : PermissionExplanation {
    var showCount = 0
    var callback: PermissionExplanation.OnConfirm? = null
    override fun show(callback: PermissionExplanation.OnConfirm) {
        showCount++
    }

    fun confirm() {
        callback?.onConfirm()
    }
}