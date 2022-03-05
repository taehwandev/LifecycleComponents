package io.androidalatan.view.event.compose.itemview

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.itemview.ItemViewEvent
import io.androidalatan.view.event.api.itemview.OnItemOfListClickEvent

class ItemViewEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : ItemViewEvent,
    OnItemOfListClickEvent by OnItemOfListClickEventImpl(id, interactionObserver)