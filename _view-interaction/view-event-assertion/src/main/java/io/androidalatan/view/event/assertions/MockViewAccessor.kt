package io.androidalatan.view.event.assertions

import androidx.annotation.VisibleForTesting
import io.androidalatan.view.event.api.ViewAccessor

class MockViewAccessor : ViewAccessor {
    private val views = hashMapOf<Int, Any>()

    @VisibleForTesting
    val variables = hashMapOf<Int, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T> view(id: Int): T {
        return views.getValue(id) as T
    }

    fun putView(id: Int, view: Any) {
        views[id] = view
    }

    override fun setVariable(id: Int, variable: Any) {
        variables[id] = variable
    }
}