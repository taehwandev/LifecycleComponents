package io.androidalatan.view.event.legacy.textview

import android.widget.TextView
import io.androidalatan.view.event.api.textview.OnTextChangeEvent
import io.androidalatan.view.event.api.textview.TextViewEvent
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.legacy.view.ViewEventImpl

class TextViewEventImpl(
    textView: TextView
) : TextViewEvent,
    ViewEvent by ViewEventImpl(textView),
    OnTextChangeEvent by OnTextChangeEventImpl(textView)