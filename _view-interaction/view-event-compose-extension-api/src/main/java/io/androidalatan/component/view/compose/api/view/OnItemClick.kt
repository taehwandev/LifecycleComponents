package io.androidalatan.component.view.compose.api.view

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent

data class OnItemClick(val index: Int, val item: Any) : ViewEvent

fun ComposeViewInteractionTrigger?.onItemClick(id: Int, index: Int, item: Any): () -> Unit {
    return {
        this?.triggerEvent(id, OnItemClick(index, item))
    }
}