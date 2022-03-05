package io.androidalatan.lifecycle.handler.compose.activity.localowners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import io.androidalatan.component.view.compose.api.ComposeViewInteractionTrigger

object LocalComposeEventTriggerOwner {
    private val LocalComposition =
        staticCompositionLocalOf<ComposeViewInteractionTrigger?> { null }

    val current: ComposeViewInteractionTrigger?
        @Composable
        get() = LocalComposition.current

    infix fun provides(registryOwner: ComposeViewInteractionTrigger):
            ProvidedValue<ComposeViewInteractionTrigger?> {
        return LocalComposition provides registryOwner
    }
}