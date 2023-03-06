package io.androidalatan.lifecycle.handler.activity

import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.MockSyncLifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

class LifecycleNotifierImplTest {

    private val invokerManager = mock<InvokerManager>()

    private val notifier = LifecycleNotifierImpl(invokerManager)
    private val caller = MockLifecycleSource()

    @Test
    fun `add in amid`() {

        notifier.triggerStarted(caller)
        reset(invokerManager)

        val lifecycleListener = MockSyncLifecycleListener()
        notifier.add(caller, lifecycleListener)

        verify(invokerManager).executeMissingEvent(
            caller,
            lifecycleListener,
            LifecycleStatus.ON_CREATED
        )
        verify(invokerManager).executeMissingEvent(
            caller,
            lifecycleListener,
            LifecycleStatus.ON_STARTED
        )
        verify(invokerManager).addMethods(eq(caller), eq(lifecycleListener), any())
        verifyNoMoreInteractions(invokerManager)

    }

    @Test
    fun `remove in amid`() {
        notifier.triggerStarted(caller)

        reset(invokerManager)

        val lifecycleListener = MockSyncLifecycleListener()
        notifier.cachedListeners.add(lifecycleListener)
        notifier.remove(caller, lifecycleListener)

        verify(invokerManager).executeMissingEvent(
            caller,
            lifecycleListener,
            LifecycleStatus.ON_DESTROY
        )
        verify(invokerManager).executeMissingEvent(
            caller,
            lifecycleListener,
            LifecycleStatus.ON_STOP
        )
        verify(invokerManager).removeMethodsOf(caller, lifecycleListener)
        verifyNoMoreInteractions(invokerManager)

    }
}