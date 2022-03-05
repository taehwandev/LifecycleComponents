package io.androidalatan.component.view.compose.model

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.ViewEvent

internal data class ViewEventCallback<T : ViewEvent>(
    @IdRes val id: Int,
    val event: Class<T>,
    val callback: ComposeViewInteractionObserver.Callback<T>
)