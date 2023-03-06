package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleHandle(content: @Composable () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleNotifier = LocalLifecycleNotifierOwner.current
    val lifecycleSource = LocalLifecycleSourceOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = lifecycleNotifier.triggerCreated(lifecycleSource)

            override fun onStart(owner: LifecycleOwner) = lifecycleNotifier.triggerStarted(lifecycleSource)

            override fun onResume(owner: LifecycleOwner) = lifecycleNotifier.triggerResumed(lifecycleSource)

            override fun onPause(owner: LifecycleOwner) = lifecycleNotifier.triggerPause(lifecycleSource)

            override fun onStop(owner: LifecycleOwner) = lifecycleNotifier.triggerStop(lifecycleSource)

            override fun onDestroy(owner: LifecycleOwner) = lifecycleNotifier.triggerDestroy(lifecycleSource)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    content.invoke()
}
