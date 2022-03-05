package io.androidalatan.component.view.compose.api.toolbar

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

object OnToolbarNavigationClick : ViewEvent

fun ComposeViewInteractionTrigger?.onToolbarNavigationClick(id: Int): () -> Unit = {
    this?.triggerEvent(id, OnToolbarNavigationClick)
}