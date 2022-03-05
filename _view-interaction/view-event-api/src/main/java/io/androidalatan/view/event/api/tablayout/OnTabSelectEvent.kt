package io.androidalatan.view.event.api.tablayout

import androidx.annotation.IntDef

interface OnTabSelectEvent {
    fun registerOnTabSelectCallback(callback: Callback)
    fun unregisterOnTabSelectCallback(callback: Callback)

    fun interface Callback {
        fun onTabSelect(selectedTab: SelectedTab)
    }

    data class SelectedTab(val position: Int, @SelectedEventType val type: Int)

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [SelectedEventType.SELECTED, SelectedEventType.UNSELECTED, SelectedEventType.RESELECTED])
    annotation class SelectedEventType {
        companion object {
            const val SELECTED = 0
            const val UNSELECTED = 1
            const val RESELECTED = 2
        }
    }
}