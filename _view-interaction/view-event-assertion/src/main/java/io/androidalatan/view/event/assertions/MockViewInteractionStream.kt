package io.androidalatan.view.event.assertions

import androidx.annotation.VisibleForTesting
import io.androidalatan.view.event.api.ViewInteractionStream

class MockViewInteractionStream(
    @VisibleForTesting val viewController: MockViewInteractionController = MockViewInteractionController()
) : ViewInteractionStream {
    override fun registerCallback(callback: ViewInteractionStream.Callback) {
        callback.onCallback(viewController)
    }

    override fun unregisterCallback(callback: ViewInteractionStream.Callback) = Unit
}