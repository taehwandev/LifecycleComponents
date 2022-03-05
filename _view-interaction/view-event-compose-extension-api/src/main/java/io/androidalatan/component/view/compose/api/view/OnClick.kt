package io.androidalatan.component.view.compose.api.view

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

object OnClick : ViewEvent

fun ComposeViewInteractionTrigger?.onClick(id: Int): () -> Unit = {
    this?.triggerEvent(id, OnClick)
}