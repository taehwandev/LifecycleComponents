package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier

object LocalLifecycleNotifierOwner {
    private val LocalComposition = staticCompositionLocalOf<LifecycleNotifier> { error("Lifecycle Notifier isn't provided") }

    val current: LifecycleNotifier
        @Composable
        get() = LocalComposition.current

    infix fun provides(registryOwner: LifecycleNotifier):
            ProvidedValue<LifecycleNotifier> {
        return LocalComposition provides registryOwner
    }
}