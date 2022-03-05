package io.androidalatan.component.view.compose.api.view

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

object OnLongClick : ViewEvent

fun ComposeViewInteractionTrigger.onLongClick(id: Int): () -> Unit = {
    triggerEvent(id, OnLongClick)
}