package io.androidalatan.view.event.api.toolbar

interface OnToolbarNavigationClick {
    fun registerOnNavigationClickEvent(callback: Callback)
    fun unregisterOnNavigationClickEvent(callback: Callback)

    fun interface Callback {
        fun onToolbarNavigationClick()
    }

}