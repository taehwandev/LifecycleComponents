package io.androidalatan.view.event.compose.tablayout

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent
import io.androidalatan.view.event.api.tablayout.TabLayoutEvent

class TabLayoutEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : TabLayoutEvent,
    OnTabSelectEvent by OnTabSelectEventImpl(id, interactionObserver)