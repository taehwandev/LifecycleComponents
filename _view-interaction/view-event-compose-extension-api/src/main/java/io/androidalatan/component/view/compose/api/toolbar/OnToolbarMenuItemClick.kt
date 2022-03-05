package io.androidalatan.component.view.compose.api.toolbar

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

data class OnToolbarMenuItemClick(@IdRes val id: Int) : ViewEvent

fun ComposeViewInteractionTrigger?.onToolbarMenuItemClick(id: Int, menuId: Int): () -> Unit = {
    this?.triggerEvent(id, OnToolbarMenuItemClick(menuId))
}