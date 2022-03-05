package io.androidalatan.compose.holder.lifecycle.activator

import org.mockito.kotlin.mock
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LifecycleActivatorTest {

    private val activator = LifecycleActivator()

    @Test
    fun `add and remove`() {
        val listener = mock<LifecycleListener>()
        activator.add(listener)

        Assertions.assertEquals(1, activator.cachedLifecycleListener.size)
        Assertions.assertEquals(listener, activator.cachedLifecycleListener.values.first())

        activator.remove(listener)
        Assertions.assertEquals(0, activator.cachedLifecycleListener.size)

    }

}