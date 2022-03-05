package io.androidalatan.view.event.legacy.compoundbutton

import android.widget.CompoundButton
import io.androidalatan.view.event.api.compoundbutton.CompoundButtonEvent
import io.androidalatan.view.event.api.compoundbutton.OnCheckedChangeEvent
import io.androidalatan.view.event.api.view.OnClickEvent
import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import io.androidalatan.view.event.api.view.OnLongClickEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.legacy.view.OnClickEventImpl
import io.androidalatan.view.event.legacy.view.OnFocusChangeEventImpl
import io.androidalatan.view.event.legacy.view.OnLongClickEventImpl
import io.androidalatan.view.event.legacy.view.OnSizeChangeEventImpl

class CompoundButtonEventImpl(
    view: CompoundButton
) : CompoundButtonEvent,
    OnClickEvent by OnClickEventImpl(view),
    OnLongClickEvent by OnLongClickEventImpl(view),
    OnSizeChangeEvent by OnSizeChangeEventImpl(view),
    OnFocusChangeEvent by OnFocusChangeEventImpl(view),
    OnCheckedChangeEvent by OnCheckedChangeEventImpl(view)
