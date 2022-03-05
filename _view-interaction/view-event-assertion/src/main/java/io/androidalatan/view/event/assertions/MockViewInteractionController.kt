package io.androidalatan.view.event.assertions

import androidx.collection.SparseArrayCompat
import io.androidalatan.view.event.api.View
import io.androidalatan.view.event.api.ViewInteractionController

class MockViewInteractionController : ViewInteractionController {
    private val viewEvents = SparseArrayCompat<MockViewEvent>()

    fun trigger(id: Int): EventTrigger = find(id)

    override fun find(id: Int): MockViewEvent = find(id, MockViewEvent::class.java)

    override fun <T : View> find(id: Int, event: Class<out T>): T {
        return (viewEvents[id] ?: MockViewEvent().apply { viewEvents.put(id, this) }) as T
    }

}