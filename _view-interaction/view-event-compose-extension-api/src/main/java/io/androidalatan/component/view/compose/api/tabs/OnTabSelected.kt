package io.androidalatan.component.view.compose.api.tabs

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent
import io.androidalatan.component.view.compose.api.holder.Holdable

data class OnTabSelected(val selectedPosition: Int) : ViewEvent, Holdable

fun ComposeViewInteractionTrigger.onTabSelected(id: Int, position: Int): () -> Unit = {
    triggerEvent(id, OnTabSelected(position))
}