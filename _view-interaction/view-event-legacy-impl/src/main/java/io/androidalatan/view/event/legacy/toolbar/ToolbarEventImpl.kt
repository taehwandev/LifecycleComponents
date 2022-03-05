package io.androidalatan.view.event.legacy.toolbar

import androidx.appcompat.widget.Toolbar
import io.androidalatan.view.event.api.toolbar.OnToolbarMenuItemClick
import io.androidalatan.view.event.api.toolbar.OnToolbarNavigationClick
import io.androidalatan.view.event.api.toolbar.ToolbarEvent

class ToolbarEventImpl(toolbar: Toolbar) : ToolbarEvent,
    OnToolbarMenuItemClick by OnToolbarMenuItemClickImpl(toolbar),
    OnToolbarNavigationClick by OnToolbarNavigationClickImpl(toolbar)
