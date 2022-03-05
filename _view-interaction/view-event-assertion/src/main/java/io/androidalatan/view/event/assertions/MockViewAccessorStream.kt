package io.androidalatan.view.event.assertions

import androidx.annotation.VisibleForTesting
import io.androidalatan.view.event.api.ViewAccessorStream

class MockViewAccessorStream(
    @VisibleForTesting val viewAccessor: MockViewAccessor = MockViewAccessor()
) : ViewAccessorStream {
    override fun registerCallback(callback: ViewAccessorStream.Callback) {
        callback.onViewAccessor(viewAccessor)
    }

    override fun unregisterCallback(callback: ViewAccessorStream.Callback) = Unit
}