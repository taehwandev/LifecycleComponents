package io.androidalatan.view.event.compose.internal

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.compoundbutton.CompoundButtonEvent
import io.androidalatan.view.event.api.itemview.ItemViewEvent
import io.androidalatan.view.event.api.tablayout.TabLayoutEvent
import io.androidalatan.view.event.api.textview.TextViewEvent
import io.androidalatan.view.event.api.toolbar.ToolbarEvent
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.compose.compoundbutton.CompoundButtonEventImpl
import io.androidalatan.view.event.compose.itemview.ItemViewEventImpl
import io.androidalatan.view.event.compose.tablayout.TabLayoutEventImpl
import io.androidalatan.view.event.compose.textview.TextViewEventImpl
import io.androidalatan.view.event.compose.toolbar.ToolbarEventImpl
import io.androidalatan.view.event.compose.view.ViewEventImpl

internal object ViewEventFactory {

    @Suppress("UNCHECKED_CAST")
    fun <T> create(event: Class<T>, @IdRes id: Int, composeViewInteractionTrigger: ComposeViewInteractionObserver): T =
        when (event) {
            ViewEvent::class.java -> ViewEventImpl(id, composeViewInteractionTrigger) as T
            TextViewEvent::class.java -> TextViewEventImpl(id, composeViewInteractionTrigger) as T
            ToolbarEvent::class.java -> ToolbarEventImpl(id, composeViewInteractionTrigger) as T
            TabLayoutEvent::class.java -> TabLayoutEventImpl(id, composeViewInteractionTrigger) as T
            CompoundButtonEvent::class.java -> CompoundButtonEventImpl(id, composeViewInteractionTrigger) as T
            ItemViewEvent::class.java -> ItemViewEventImpl(id, composeViewInteractionTrigger) as T
            else -> {
                throw UnsupportedOperationException("We don't support this yet : ${event.name}")
            }
        }
}