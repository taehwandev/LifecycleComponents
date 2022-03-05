package io.androidalatan.view.event.api

import androidx.annotation.IdRes

interface ViewAccessor {
    fun <T> view(@IdRes id: Int): T
    fun setVariable(@IdRes id: Int, variable: Any)
}