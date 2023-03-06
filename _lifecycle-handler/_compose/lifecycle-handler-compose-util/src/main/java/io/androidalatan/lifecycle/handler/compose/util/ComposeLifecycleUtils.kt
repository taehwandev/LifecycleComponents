package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import io.androidalatan.lifecycle.handler.api.LifecycleListener

@Composable
fun <T : LifecycleListener> T.activate(): T {
    val lifecycleNotifier = LocalLifecycleNotifierOwner.current
    val lifecycleSource = LocalLifecycleSourceOwner.current

    val lifecycleListener = remember { this }

    DisposableEffect(lifecycleListener) {
        lifecycleNotifier.add(lifecycleSource, lifecycleListener)

        onDispose {
            lifecycleNotifier.remove(lifecycleSource, lifecycleListener)
        }
    }
    return lifecycleListener
}
