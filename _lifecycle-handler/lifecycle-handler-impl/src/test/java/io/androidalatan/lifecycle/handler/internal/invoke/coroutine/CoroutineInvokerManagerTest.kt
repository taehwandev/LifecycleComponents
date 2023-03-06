package io.androidalatan.lifecycle.handler.internal.invoke.coroutine

import io.androidalatan.coroutine.dispatcher.api.assertion.MockDispatcherProvider
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.annotations.sync.OnDestroy
import io.androidalatan.lifecycle.handler.annotations.sync.OnPause
import io.androidalatan.lifecycle.handler.annotations.sync.OnResumed
import io.androidalatan.lifecycle.handler.internal.invoke.MockCoroutineLifecycleListener
import io.androidalatan.lifecycle.handler.internal.invoke.SyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.model.SyncInvokerInfo
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CoroutineInvokerManagerTest {

    private val dispatcherProvider = MockDispatcherProvider()
    private val coroutineInvokerManager = CoroutineInvokerManagerImpl(dispatcherProvider)
    private val manager = SyncInvokerManager(coroutineInvokerManager)
    private val caller = Any()

    @Test
    fun invokerListTest() {
        val lifecycleListener = MockCoroutineLifecycleListener()

        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == "execute" })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))

        Assertions.assertEquals(
            mapOf(methodInfo.annotationKClass to listOf(SyncInvokerInfo(methodInfo.method, true, false))),
            manager.invokers[lifecycleListener]
        )
    }

    @Test
    fun suspendOnCreatedTest() {
        val methodName = "execute"
        val lifecycleListener = MockCoroutineLifecycleListener()

        val methodInfo = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == methodName })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))

        Assertions.assertEquals(
            mapOf(methodInfo.annotationKClass to listOf(SyncInvokerInfo(methodInfo.method, true, false))),
            manager.invokers[lifecycleListener]
        )

        manager.execute(caller, LifecycleStatus.ON_CREATED)

        Assertions.assertTrue(coroutineInvokerManager.coroutineScope.isActive)
        Assertions.assertEquals(1, lifecycleListener.executedCount)
    }

    @Test
    fun suspendUiDispatcherTest() {
        val methodName = "resume"
        val lifecycleListener = MockCoroutineLifecycleListener()

        val methodInfo = MethodInfo(OnResumed::class, lifecycleListener::class.java.declaredMethods.first { it.name == methodName })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))

        Assertions.assertEquals(
            mapOf(methodInfo.annotationKClass to listOf(SyncInvokerInfo(methodInfo.method, true, true))),
            manager.invokers[lifecycleListener]
        )

        manager.execute(caller, LifecycleStatus.ON_RESUMED)

        Assertions.assertTrue(coroutineInvokerManager.coroutineScope.isActive)
        Assertions.assertEquals(1, lifecycleListener.executedCount)
    }

    @Test
    fun suspendPauseTest() {
        val methodName = "pause"
        val lifecycleListener = MockCoroutineLifecycleListener()

        val methodInfo = MethodInfo(OnPause::class, lifecycleListener::class.java.declaredMethods.first { it.name == methodName })
        manager.addMethods(caller, lifecycleListener, listOf(methodInfo))

        Assertions.assertEquals(
            mapOf(methodInfo.annotationKClass to listOf(SyncInvokerInfo(methodInfo.method, true, false))),
            manager.invokers[lifecycleListener]
        )

        manager.execute(caller, LifecycleStatus.ON_PAUSE)

        Assertions.assertTrue(coroutineInvokerManager.coroutineScope.isActive)
        Assertions.assertEquals(1, lifecycleListener.executedCount)
    }

    @Test
    fun suspendOnDestroyTest() {
        val lifecycleListener = MockCoroutineLifecycleListener()

        val methodInfoOnCreated = MethodInfo(OnCreated::class, lifecycleListener::class.java.declaredMethods.first { it.name == "execute" })
        val methodInfoOnDestroy = MethodInfo(OnDestroy::class, lifecycleListener::class.java.declaredMethods.first { it.name == "clear" })
        manager.addMethods(
            caller,
            lifecycleListener,
            listOf(methodInfoOnCreated, methodInfoOnDestroy)
        )

        Assertions.assertEquals(
            mapOf(
                methodInfoOnCreated.annotationKClass to listOf(SyncInvokerInfo(methodInfoOnCreated.method, true, false)),
                methodInfoOnDestroy.annotationKClass to listOf(SyncInvokerInfo(methodInfoOnDestroy.method, true, false)),
            ),
            manager.invokers[lifecycleListener]
        )

        manager.execute(caller, LifecycleStatus.ON_CREATED)
        Assertions.assertEquals(1, lifecycleListener.executedCount)
        Assertions.assertTrue(coroutineInvokerManager.coroutineScope.isActive)
        Assertions.assertFalse(coroutineInvokerManager.coroutineScope.coroutineContext.job.isCancelled)

        manager.execute(caller, LifecycleStatus.ON_DESTROY)
        Assertions.assertEquals(0, lifecycleListener.executedCount)
        Assertions.assertTrue(coroutineInvokerManager.coroutineScope.isActive)
        Assertions.assertFalse(coroutineInvokerManager.coroutineScope.coroutineContext.job.isCancelled)
    }
}
