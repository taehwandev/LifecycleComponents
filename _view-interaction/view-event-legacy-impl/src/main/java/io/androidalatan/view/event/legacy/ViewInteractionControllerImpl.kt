package io.androidalatan.view.event.legacy

import androidx.annotation.VisibleForTesting
import androidx.collection.SimpleArrayMap
import androidx.collection.SparseArrayCompat
import io.androidalatan.view.event.api.ViewAccessor
import io.androidalatan.view.event.api.ViewInteractionController
import io.androidalatan.view.event.api.view.ViewEvent
import io.androidalatan.view.event.legacy.internal.ViewEventFactory

class ViewInteractionControllerImpl(
    private val viewAccessor: ViewAccessor
) : ViewInteractionController {

    @VisibleForTesting
    internal val viewCache = SparseArrayCompat<SimpleArrayMap<Class<*>, Any>>()

    override fun find(id: Int): ViewEvent = find(id, ViewEvent::class.java)
    override fun <T : io.androidalatan.view.event.api.View> find(id: Int, event: Class<out T>): T {
        val eventCache: SimpleArrayMap<Class<*>, Any> = viewCache[id] ?: SimpleArrayMap<Class<*>, Any>().apply {
            viewCache.put(id, this)
        }

        return eventCache[event] as? T ?: ViewEventFactory.create(event, viewAccessor.view(id))
            .apply {
                eventCache.put(event, this)
            }
    }

}