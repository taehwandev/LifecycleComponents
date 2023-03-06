package io.androidalatan.lifecycle.handler.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import io.androidalatan.lifecycle.handler.api.LifecycleSource

object LocalLifecycleSourceOwner {
    private val LocalComposition = staticCompositionLocalOf<LifecycleSource> { error("Lifecycle Source isn't provided") }

    val current: LifecycleSource
        @Composable
        get() = LocalComposition.current

    infix fun provides(registryOwner: LifecycleSource):
            ProvidedValue<LifecycleSource> {
        return LocalComposition provides registryOwner
    }
}