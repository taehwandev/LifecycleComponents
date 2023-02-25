package io.androidalatan.lifecycle.handler.store

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

internal class LifecycleViewModelStoreOwnerImplTest {

    private val owner = LifecycleViewModelStoreOwnerImpl()

    @Test
    fun `test cache`() {
        val lifecycleListener = mock<LifecycleListener>()
        owner.cached(lifecycleListener)
        assertEquals(lifecycleListener, owner.getLifecycleListener(lifecycleListener::class.qualifiedName))
    }

    @Test
    fun `test remove`() {
        val lifecycleListener = mock<LifecycleListener>()
        lifecycleListener::class.qualifiedName?.let { key ->
            owner.cached[key] = lifecycleListener

            owner.remove(lifecycleListener)

            assertThrows(Exception::class.java) {
                owner.getLifecycleListener(key)
            }

            assertTrue(owner.cached.isEmpty())
        }
    }
}