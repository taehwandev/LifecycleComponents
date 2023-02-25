package io.androidalatan.lifecycle.handler.compose.activity.localowners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import io.androidalatan.lifecycle.handler.api.LifecycleViewModelStoreOwner

object LocalLifecycleViewModelStoreOwner {

    private val LocalComposition = staticCompositionLocalOf<LifecycleViewModelStoreOwner> {
        error("No LifecycleViewModelStoreOwner Trigger isn't provided")
    }

    val current: LifecycleViewModelStoreOwner
        @Composable
        get() = LocalComposition.current

    infix fun provides(
        lifecycleViewModelStoreOwner: LifecycleViewModelStoreOwner
    ): ProvidedValue<LifecycleViewModelStoreOwner> =
        LocalComposition provides lifecycleViewModelStoreOwner
}