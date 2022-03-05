package io.androidalatan.request.permission

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import java.lang.ref.WeakReference

class PermissionExplanationImpl(
    private val contextRef: WeakReference<Context>,
    private val builder: PermissionExplanationBuilder
) : PermissionExplanation {
    override fun show(callback: PermissionExplanation.OnConfirm) {
        val context = contextRef.get() ?: return

        val alertDialogBuilder = AlertDialog.Builder(context)

        if (builder.title.isNotEmpty()) {
            alertDialogBuilder.setTitle(builder.title)
        } else if (builder.titleResId != View.NO_ID) {
            alertDialogBuilder.setTitle(builder.titleResId)
        }

        if (builder.message.isNotEmpty()) {
            alertDialogBuilder.setMessage(builder.message)
        } else if (builder.messageResId != View.NO_ID) {
            alertDialogBuilder.setMessage(builder.messageResId)
        }

        if (builder.confirmButtonText.isNotEmpty()) {
            alertDialogBuilder.setPositiveButton(builder.confirmButtonText) { _, _ ->
                callback.onConfirm()
            }
        } else if (builder.confirmButtonTextResId != View.NO_ID) {
            alertDialogBuilder.setPositiveButton(builder.confirmButtonTextResId) { _, _ ->
                callback.onConfirm()
            }
        }

        alertDialogBuilder.create()
            .show()

    }
}