package io.androidalatan.view.event.impl

import androidx.annotation.VisibleForTesting
import io.androidalatan.view.event.api.ViewInteractionController
import io.androidalatan.view.event.api.ViewInteractionStream

class ViewInteractionStreamImpl : ViewInteractionStream {

    @VisibleForTesting
    internal var callbacks = mutableListOf<ViewInteractionStream.Callback>()

    @VisibleForTesting
    internal var viewControllers = emptyList<ViewInteractionController>()

    fun setViewController(vararg viewController: ViewInteractionController) {
        synchronized(this) {
            this.viewControllers = viewController.toList()
            executeCallbacks()
        }
    }

    private fun executeCallbacks() {
        synchronized(this) {
            callbacks.forEach { callback ->
                viewControllers.forEach { viewController ->
                    callback.onCallback(viewController)
                }
            }
        }
    }

    override fun registerCallback(callback: ViewInteractionStream.Callback) {
        synchronized(this) {
            callbacks.add(callback)
            viewControllers.forEach { callback.onCallback(it) }
        }
    }

    override fun unregisterCallback(callback: ViewInteractionStream.Callback) {
        synchronized(this) {
            callbacks.remove(callback)
        }
    }

}