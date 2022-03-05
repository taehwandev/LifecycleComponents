package io.androidalatan.request.permission.api

import android.view.View
import androidx.annotation.StringRes

abstract class PermissionExplanationBuilder {

    var title: String = ""
        private set

    @StringRes
    var titleResId: Int = View.NO_ID
        private set

    var message: String = ""
        private set

    @StringRes
    var messageResId: Int = View.NO_ID
        private set

    var confirmButtonText: String = ""
        private set

    @StringRes
    var confirmButtonTextResId: Int = View.NO_ID
        private set

    fun title(title: String): PermissionExplanationBuilder {
        this.title = title
        return this
    }

    fun titleResId(@StringRes titleResId: Int): PermissionExplanationBuilder {
        this.titleResId = titleResId
        return this
    }

    fun message(message: String): PermissionExplanationBuilder {
        this.message = message
        return this
    }

    fun messageResId(@StringRes messageResId: Int): PermissionExplanationBuilder {
        this.messageResId = messageResId
        return this
    }

    fun confirmButtonText(confirmButtonText: String): PermissionExplanationBuilder {
        this.confirmButtonText = confirmButtonText
        return this
    }

    fun confirmButtonTextResId(@StringRes confirmButtonTextResId: Int): PermissionExplanationBuilder {
        this.confirmButtonTextResId = confirmButtonTextResId
        return this
    }

    abstract fun build(): PermissionExplanation
}