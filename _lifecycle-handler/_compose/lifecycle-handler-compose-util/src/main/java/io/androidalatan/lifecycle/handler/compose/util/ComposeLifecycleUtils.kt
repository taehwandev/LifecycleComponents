package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import io.androidalatan.lifecycle.handler.api.LifecycleListener

@Composable
fun <T : LifecycleListener> T.activate(): T {
    val lifecycleNotifier = LocalLifecycleNotifierOwner.current

    val lifecycleListener = remember { this }

    DisposableEffect(lifecycleListener) {
        lifecycleNotifier.add(lifecycleListener)

        onDispose {
            lifecycleNotifier.remove(lifecycleListener)
        }
    }
    return lifecycleListener
}
