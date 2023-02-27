package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalLifecycleViewModelStoreOwner

@Composable
fun <T : LifecycleListener> T.activate(): T {
    val lifecycleNotifier = LocalLifecycleNotifierOwner.current
    val lifecycleViewModelStoreOwner = LocalLifecycleViewModelStoreOwner.current

    val lifecycleListener = remember { this }
    lifecycleViewModelStoreOwner.cached(lifecycleListener)

    DisposableEffect(lifecycleListener) {
        lifecycleNotifier.add(lifecycleListener)

        onDispose {
            lifecycleNotifier.remove(lifecycleListener)
            lifecycleViewModelStoreOwner.remove(lifecycleListener)
        }
    }
    return lifecycleListener
}
