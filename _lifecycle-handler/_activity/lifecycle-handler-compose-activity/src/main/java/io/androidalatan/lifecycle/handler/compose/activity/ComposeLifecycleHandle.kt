package io.androidalatan.lifecycle.handler.compose.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.androidalatan.lifecycle.handler.compose.util.LocalLifecycleNotifierOwner

@Composable
internal fun LifecycleHandle(content: @Composable () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleNotifier = LocalLifecycleNotifierOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                lifecycleNotifier.triggerCreated()
            }

            override fun onStart(owner: LifecycleOwner) {
                lifecycleNotifier.triggerStarted()
            }

            override fun onResume(owner: LifecycleOwner) {
                lifecycleNotifier.triggerResumed()
            }

            override fun onPause(owner: LifecycleOwner) {
                lifecycleNotifier.triggerPause()
            }

            override fun onStop(owner: LifecycleOwner) {
                lifecycleNotifier.triggerStop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                lifecycleNotifier.triggerDestroy()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    content.invoke()
}
