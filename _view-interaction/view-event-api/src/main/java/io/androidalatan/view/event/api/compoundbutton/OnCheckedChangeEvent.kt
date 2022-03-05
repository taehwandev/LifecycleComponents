package io.androidalatan.view.event.api.compoundbutton

interface OnCheckedChangeEvent  {
    fun registerOnCheckChangeCallback(callback: Callback)
    fun unregisterOnCheckChangeCallback(callback: Callback)

    fun interface Callback {
        fun onCheckChange(checked: Boolean)
    }

}