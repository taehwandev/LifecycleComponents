package io.androidalatan.view.event.compose.compoundbutton

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.compoundbutton.CompoundButtonEvent
import io.androidalatan.view.event.api.compoundbutton.OnCheckedChangeEvent
import io.androidalatan.view.event.api.view.OnClickEvent
import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import io.androidalatan.view.event.api.view.OnLongClickEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.compose.view.OnClickEventImpl
import io.androidalatan.view.event.compose.view.OnFocusChangeEventImpl
import io.androidalatan.view.event.compose.view.OnLongClickEventImpl
import io.androidalatan.view.event.compose.view.OnSizeChangeEventImpl

class CompoundButtonEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : CompoundButtonEvent,
    OnClickEvent by OnClickEventImpl(id, interactionObserver),
    OnLongClickEvent by OnLongClickEventImpl(id, interactionObserver),
    OnSizeChangeEvent by OnSizeChangeEventImpl(id, interactionObserver),
    OnFocusChangeEvent by OnFocusChangeEventImpl(id, interactionObserver),
    OnCheckedChangeEvent by OnCheckedChangeEventImpl(id, interactionObserver)
