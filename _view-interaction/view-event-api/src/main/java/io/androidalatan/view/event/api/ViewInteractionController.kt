package io.androidalatan.view.event.api

import androidx.annotation.IdRes
import io.androidalatan.view.event.api.view.ViewEvent

interface ViewInteractionController {
    fun find(@IdRes id: Int): ViewEvent
    fun <T : View> find(@IdRes id: Int, event: Class<out T>): T
}