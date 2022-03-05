package io.androidalatan.view.event.api.toolbar

interface OnToolbarMenuItemClick {
    fun registerOnMenuItemClickEvent(callback: Callback)
    fun unregisterOnMenuItemClickEvent(callback: Callback)

    fun interface Callback {
        fun onNavigationMenuItemClick(itemId: Int)
    }

}