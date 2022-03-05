package io.androidalatan.component.view.compose.api.textfield

import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent
import io.androidalatan.component.view.compose.api.holder.Holdable

data class OnFocusChanged(val focused: Boolean) : ViewEvent, Holdable

fun <T> ComposeViewInteractionTrigger.onFocusChanged(
    id: Int,
    focusedCalculation: (T) -> Boolean
): (T) -> Unit = { focusedValue ->
    triggerEvent(id, OnFocusChanged(focusedCalculation.invoke(focusedValue)))
}