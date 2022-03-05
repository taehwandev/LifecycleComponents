package io.androidalatan.component.view.compose.api.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger
import io.androidalatan.component.view.compose.api.ViewEvent
import io.androidalatan.component.view.compose.api.holder.Holdable

data class OnTextChanged(val newText: String) : ViewEvent, Holdable

data class TextTriggerEvent(val text: String, val textChangeEvent: (String) -> Unit)

/**
 * This is for two way binding. Whether user input or view model member field should populate text field, you should use it
 */
@Composable
fun ComposeViewInteractionTrigger?.textTriggerEvent(id: Int, updatableValue: MutableState<String>): TextTriggerEvent {

    var textValue by updatableValue

    return TextTriggerEvent(textValue) { newText ->
        this?.triggerEvent(id, OnTextChanged(newText))
        if (textValue != newText) {
            textValue = newText
        }
    }
}

/**
 * This is for one way binding. You should use this to populate text by user input
 */
@Composable
fun ComposeViewInteractionTrigger?.textTriggerEvent(id: Int, initValue: String): TextTriggerEvent {

    var textValue by remember { mutableStateOf(initValue) }

    return TextTriggerEvent(textValue) { newText ->
        textValue = newText
        this?.triggerEvent(id, OnTextChanged(newText))
    }
}

fun ComposeViewInteractionTrigger?.onTextChanged(id: Int): (String) -> Unit {
    return { newText -> this?.triggerEvent(id, OnTextChanged(newText)) }
}