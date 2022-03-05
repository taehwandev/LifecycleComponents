package io.androidalatan.view.event.api.textview

interface OnTextChangeEvent {
    fun registerOnTextChangeEvent(callback: Callback)
    fun unregisterOnTextChangeEvent(callback: Callback)

    fun interface Callback {
        fun onTextChange(textInfo: TextChangeInfo)
    }

    data class TextChangeInfo(
        val text: String = "",
        val start: Int = 0,
        val count: Int = 0,
        val beforeOrAfter: Int = 0
    )

}