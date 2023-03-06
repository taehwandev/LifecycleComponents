package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.coroutine.dispatcher.api.assertion.MockDispatcherProvider
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.internal.invoke.coroutine.CoroutineInvokerManagerImpl
import io.androidalatan.lifecycle.handler.internal.invoke.model.SyncInvokerInfo
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.reflect.Method
import kotlin.reflect.KClass

class SyncInvokerManagerTest {

    private val manager = SyncInvokerManager(
        coroutineInvokerManager = CoroutineInvokerManagerImpl(MockDispatcherProvider())
    )
    private val caller = Any()

    @Test
    fun `addMethods emptyList`() {
        Assertions.assertEquals(0, manager.invokers.size)
        val lifecycleListener = MockSyncLifecycleListener()
        manager.addMethods(caller, lifecycleListener, emptyList())
        Assertions.assertEquals(1, manager.invokers.size)
        Assertions.assertEquals(emptyMap<KClass<out Annotation>, List<Method>>(), manager.invokers[lifecycleListener])
    }

    @Test
    fun `addMethods valid values`() {
        Assertions.assertEquals(0, manager.invokers.size)
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods[0])
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))
        Assertions.assertEquals(1, manager.invokers.size)
        Assertions.assertEquals(
            mapOf(methodInfo.annotationKClass to listOf(SyncInvokerInfo(methodInfo.method))),
            manager.invokers[lifecycleListener]
        )
    }

    @Test
    fun removeMethodsOf() {
        Assertions.assertEquals(0, manager.invokers.size)
        val lifecycleListener = MockSyncLifecycleListener()
        manager.addMethods(caller, lifecycleListener, emptyList())
        Assertions.assertEquals(1, manager.invokers.size)

        manager.removeMethodsOf(this, lifecycleListener)
        Assertions.assertEquals(0, manager.invokers.size)
    }

    @Test
    fun `execute with valid method`() {
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == "execute" })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))

        manager.execute(caller, LifecycleStatus.ON_CREATED)

        Assertions.assertEquals(1, lifecycleListener.executedCount)
    }

    @Test
    fun `execute without method`() {
        val lifecycleListener = MockSyncLifecycleListener()
        manager.execute(caller, LifecycleStatus.ON_CREATED)
        Assertions.assertEquals(0, lifecycleListener.executedCount)
    }

    @Test
    fun executeMissingEvent() {
        val lifecycleListener = MockSyncLifecycleListener()
        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == "execute" })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))
        manager.executeMissingEvent(caller, lifecycleListener, LifecycleStatus.ON_CREATED)

        Assertions.assertEquals(1, lifecycleListener.executedCount)

    }
}