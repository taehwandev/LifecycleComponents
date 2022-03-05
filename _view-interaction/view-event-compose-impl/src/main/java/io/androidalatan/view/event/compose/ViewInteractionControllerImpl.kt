package io.androidalatan.view.event.compose

import androidx.annotation.VisibleForTesting
import androidx.collection.SimpleArrayMap
import androidx.collection.SparseArrayCompat
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.view.event.api.View
import io.androidalatan.view.event.api.ViewInteractionController
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.compose.internal.ViewEventFactory

class ViewInteractionControllerImpl(
    private val eventTrigger: ComposeViewInteractionObserver
) : ViewInteractionController {

    @VisibleForTesting
    internal val viewCache = SparseArrayCompat<SimpleArrayMap<Class<*>, Any>>()

    override fun find(id: Int): ViewEvent = find(id, ViewEvent::class.java)

    override fun <T : View> find(id: Int, event: Class<out T>): T {
        val eventCache: SimpleArrayMap<Class<*>, Any> = viewCache[id] ?: SimpleArrayMap<Class<*>, Any>().apply {
            viewCache.put(id, this)
        }

        return eventCache[event] as? T ?: ViewEventFactory.create(event, id, eventTrigger)
            .apply {
                eventCache.put(event, this)
            }

    }
}