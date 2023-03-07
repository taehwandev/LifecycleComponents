package io.androidalatan.lifecycle.handler.compose.cache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf

object LocalComposeCacheOwner {

    private val LocalComposition = staticCompositionLocalOf<ComposeCache> {
        error("No LocalCacheStoreOwner Trigger isn't provided")
    }

    val current: ComposeCache
        @Composable
        get() = LocalComposition.current

    infix fun provides(
        localCacheStoreOwner: ComposeCache
    ): ProvidedValue<ComposeCache> =
        LocalComposition provides localCacheStoreOwner
}