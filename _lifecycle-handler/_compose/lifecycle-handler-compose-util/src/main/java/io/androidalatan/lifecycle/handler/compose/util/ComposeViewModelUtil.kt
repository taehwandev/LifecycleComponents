package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalLifecycleViewModelStoreOwner

@Composable
inline fun <reified VM : LifecycleListener> lifecycleViewModel(): VM {
    val lifecycleViewModelStoreOwner = LocalLifecycleViewModelStoreOwner.current
    return remember { lifecycleViewModelStoreOwner.getLifecycleListener(VM::class.qualifiedName) as VM }
}