package io.androidalatan.view.event.compose.toolbar

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.toolbar.OnToolbarMenuItemClick
import io.androidalatan.view.event.api.toolbar.OnToolbarNavigationClick
import io.androidalatan.view.event.api.toolbar.ToolbarEvent

class ToolbarEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : ToolbarEvent,
    OnToolbarMenuItemClick by OnToolbarMenuItemClickImpl(id, interactionObserver),
    OnToolbarNavigationClick by OnToolbarNavigationClickImpl(id, interactionObserver)
