package io.androidalatan.view.event.compose.view

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.view.OnClickEvent
import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import io.androidalatan.view.event.api.view.OnLongClickEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.api.view.ViewEvent

class ViewEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : ViewEvent,
    OnClickEvent by OnClickEventImpl(id, interactionObserver),
    OnLongClickEvent by OnLongClickEventImpl(id, interactionObserver),
    OnSizeChangeEvent by OnSizeChangeEventImpl(id, interactionObserver),
    OnFocusChangeEvent by OnFocusChangeEventImpl(id, interactionObserver)