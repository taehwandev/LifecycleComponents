package io.androidalatan.view.event.compose.textview

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.textview.OnTextChangeEvent
import io.androidalatan.view.event.api.textview.TextViewEvent
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.compose.view.ViewEventImpl

class TextViewEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : TextViewEvent,
    ViewEvent by ViewEventImpl(id, interactionObserver),
    OnTextChangeEvent by OnTextChangeEventImpl(id, interactionObserver)