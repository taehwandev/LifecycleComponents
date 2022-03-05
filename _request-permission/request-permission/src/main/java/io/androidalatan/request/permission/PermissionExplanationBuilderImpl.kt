package io.androidalatan.request.permission

import android.content.Context
import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import java.lang.ref.WeakReference

class PermissionExplanationBuilderImpl(
    private val context: WeakReference<Context>,
) : PermissionExplanationBuilder() {
    override fun build(): PermissionExplanation {
        return PermissionExplanationImpl(context, this)
    }
}