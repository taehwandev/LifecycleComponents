package io.androidalatan.view.event.legacy.internal

import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import io.androidalatan.view.event.api.compoundbutton.CompoundButtonEvent
import io.androidalatan.view.event.api.tablayout.TabLayoutEvent
import io.androidalatan.view.event.api.textview.TextViewEvent
import io.androidalatan.view.event.api.toolbar.ToolbarEvent
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.legacy.compoundbutton.CompoundButtonEventImpl
import io.androidalatan.view.event.legacy.tablayout.TabLayoutEventImpl
import io.androidalatan.view.event.legacy.textview.TextViewEventImpl
import io.androidalatan.view.event.legacy.toolbar.ToolbarEventImpl
import io.androidalatan.view.event.legacy.view.ViewEventImpl

internal object ViewEventFactory {

    @Suppress("UNCHECKED_CAST")
    fun <T : io.androidalatan.view.event.api.View> create(event: Class<out T>, view: View): T = when (event) {
        ViewEvent::class.java -> {
            ViewEventImpl(view) as T
        }
        TextViewEvent::class.java -> {
            require(view is TextView) { "${view.id} is not TextView" }
            TextViewEventImpl(view) as T
        }
        CompoundButtonEvent::class.java -> {
            require(view is CompoundButton) { "${view.id} is not CompoundButton" }
            CompoundButtonEventImpl(view) as T
        }
        ToolbarEvent::class.java -> {
            require(view is Toolbar) { "${view.id} is not Toolbar" }
            ToolbarEventImpl(view) as T
        }
        TabLayoutEvent::class.java -> {
            require(view is TabLayout) { "${view.id} is not TabLayout" }
            TabLayoutEventImpl(view) as T
        }
        else -> {
            throw UnsupportedOperationException("What do you want from me? : ${event.name}")
        }
    }
}