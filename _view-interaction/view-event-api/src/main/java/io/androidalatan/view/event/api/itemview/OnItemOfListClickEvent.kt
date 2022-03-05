package io.androidalatan.view.event.api.itemview

interface OnItemOfListClickEvent {
    fun registerOnItemClickEvent(callback: Callback)
    fun unregisterOnItemClickEvent(callback: Callback)

    data class ClickedItem<T>(val index: Int, val item: T)

    interface Callback {
        fun <T> onItemClick(item: ClickedItem<T>)
    }
}