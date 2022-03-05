package io.androidalatan.view.event.legacy.view

import android.view.View
import io.androidalatan.view.event.api.view.OnClickEvent
import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import io.androidalatan.view.event.api.view.OnLongClickEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.api.view.ViewEvent

class ViewEventImpl(
    view: View
) : ViewEvent,
    OnClickEvent by OnClickEventImpl(view),
    OnLongClickEvent by OnLongClickEventImpl(view),
    OnSizeChangeEvent by OnSizeChangeEventImpl(view),
    OnFocusChangeEvent by OnFocusChangeEventImpl(view)