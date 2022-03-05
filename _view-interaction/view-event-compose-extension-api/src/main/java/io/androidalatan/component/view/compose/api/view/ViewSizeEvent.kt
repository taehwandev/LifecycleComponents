package io.androidalatan.component.view.compose.api.view

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

data class ViewSizeEvent(val width: Int, val height: Int) : ViewEvent

fun ComposeViewInteractionTrigger?.viewSizeEvent(id: Int, width: Int, height: Int) =
    this?.triggerEvent(id, ViewSizeEvent(width, height))