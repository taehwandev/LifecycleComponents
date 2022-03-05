package io.androidalatan.lifecycle.handler.compose.activity.localowners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import io.androidalatan.component.view.compose.api.ComposeKeyboardController

object LocalComposeKeyboardControllerOwner {
    private val LocalComposition =
        staticCompositionLocalOf<ComposeKeyboardController?> { null }

    val current: ComposeKeyboardController?
        @Composable
        get() = LocalComposition.current

    infix fun provides(registryOwner: ComposeKeyboardController):
            ProvidedValue<ComposeKeyboardController?> {
        return LocalComposition provides registryOwner
    }
}