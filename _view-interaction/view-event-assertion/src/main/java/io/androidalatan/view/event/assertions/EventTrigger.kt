package io.androidalatan.view.event.assertions

import io.androidalatan.view.event.api.itemview.OnItemOfListClickEvent
import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent
import io.androidalatan.view.event.api.textview.OnTextChangeEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent

interface EventTrigger {

    fun click()
    fun longClick()
    fun newFocus(newFocused: Boolean)
    fun newSize(newSize: OnSizeChangeEvent.ViewSize)
    fun newText(newText: OnTextChangeEvent.TextChangeInfo)
    fun newCheck(newChecked: Boolean)
    fun itemClick(item: OnItemOfListClickEvent.ClickedItem<Any>)
    fun newTab(newTab: OnTabSelectEvent.SelectedTab)
    fun menuItemClick(itemId: Int)
    fun onNavClick()
}