package io.androidalatan.component.view.compose.api.compoundbutton

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent
import io.androidalatan.component.view.compose.api.holder.Holdable

data class OnCheckedChanged(val checked: Boolean) : ViewEvent, Holdable

fun ComposeViewInteractionTrigger.onCheckedChanged(id: Int): (Boolean) -> Unit = { checked ->
    triggerEvent(id, OnCheckedChanged(checked))
}