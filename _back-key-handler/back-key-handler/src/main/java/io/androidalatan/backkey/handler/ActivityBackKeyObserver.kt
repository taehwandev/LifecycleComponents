package io.androidalatan.backkey.handler

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.VisibleForTesting
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.backkey.handler.api.BackKeyObserver

class ActivityBackKeyObserver(private val activity: ComponentActivity) : BackKeyObserver {
    @VisibleForTesting
    internal var onBackPressedCallback: OnBackPressedCallback? = null
    override fun initIfNeed(handlerCallbacks: () -> List<BackKeyHandlerStream.Callback>) {
        if (onBackPressedCallback == null) {

            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    handlerCallbacks().forEach { it.onCallback() }
                }
            }
            activity.onBackPressedDispatcher.addCallback(activity, onBackPressedCallback)
            this.onBackPressedCallback = onBackPressedCallback
        } else {
            onBackPressedCallback?.isEnabled = true
        }
    }

    override fun deinit() {
        onBackPressedCallback?.isEnabled = false
    }

}