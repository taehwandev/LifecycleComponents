package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.ListenerAnalyzer
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class InvokerManagerImplTest {

    private val asyncInvokerManager = MockInvokerManager()
    private val syncInvokerManager = MockInvokerManager()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val manager = InvokerManagerImpl(
        asyncInvokerManager = asyncInvokerManager,
        syncInvokerManager = syncInvokerManager
    )
    private val analyzer = ListenerAnalyzer()
    private val caller = Any()

    @Test
    fun addMethods() {
        val syncLifecycleListener = MockSyncLifecycleListener()
        val methodsOfSync = analyzer.analyze(syncLifecycleListener)
        manager.addMethods(caller, syncLifecycleListener, methodsOfSync.toList())
        Assertions.assertEquals(1, syncInvokerManager.addMethodCount)
        Assertions.assertEquals(0, asyncInvokerManager.addMethodCount)
        syncInvokerManager.reset()
        asyncInvokerManager.reset()

        val asyncLifecycleListener = MockFlowLifecycleListener()
        val methodsOfAsync = analyzer.analyze(asyncLifecycleListener)
        manager.addMethods(caller, asyncLifecycleListener, methodsOfAsync.toList())
        Assertions.assertEquals(0, syncInvokerManager.addMethodCount)
        Assertions.assertEquals(1, asyncInvokerManager.addMethodCount)

    }

    @Test
    fun removeMethodsOf() {
        val syncLifecycleListener = MockSyncLifecycleListener()
        manager.removeMethodsOf(caller, syncLifecycleListener)
        Assertions.assertEquals(1, syncInvokerManager.removeMethodCount)
        Assertions.assertEquals(1, asyncInvokerManager.removeMethodCount)
        syncInvokerManager.reset()
        asyncInvokerManager.reset()

        val asyncLifecycleListener = MockFlowLifecycleListener()
        manager.removeMethodsOf(caller, asyncLifecycleListener)
        Assertions.assertEquals(1, syncInvokerManager.removeMethodCount)
        Assertions.assertEquals(1, asyncInvokerManager.removeMethodCount)
        syncInvokerManager.reset()
        asyncInvokerManager.reset()

    }

    @Test
    fun execute() {
        manager.execute(manager, LifecycleStatus.ON_CREATED)
        Assertions.assertEquals(1, syncInvokerManager.executeCount)
        Assertions.assertEquals(1, asyncInvokerManager.executeCount)
    }

    @Test
    fun executeMissingEvent() {
        val lifecycleListener = mock<LifecycleListener>()
        val currentStatus = LifecycleStatus.ON_CREATED
        manager.executeMissingEvent(caller, lifecycleListener, currentStatus)
        Assertions.assertEquals(1, syncInvokerManager.executeMissingCount)
        Assertions.assertEquals(lifecycleListener, syncInvokerManager.lastLifecycleListener)
        Assertions.assertEquals(currentStatus, syncInvokerManager.lastExecutedStatus)

        Assertions.assertEquals(1, asyncInvokerManager.executeMissingCount)
        Assertions.assertEquals(lifecycleListener, asyncInvokerManager.lastLifecycleListener)
        Assertions.assertEquals(currentStatus, asyncInvokerManager.lastExecutedStatus)
    }
}