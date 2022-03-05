package io.androidalatan.lifecycle.handler.activity

import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.internal.invoke.AsyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManagerImpl
import io.androidalatan.lifecycle.handler.internal.invoke.SyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.coroutine.CoroutineInvokerManagerImpl
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer

abstract class LifecycleService : androidx.lifecycle.LifecycleService(), LifecycleSource {

    private val invokeManager = InvokerManagerImpl(
        syncInvokerManager = SyncInvokerManager(
            coroutineInvokerManager = CoroutineInvokerManagerImpl(DispatcherProvider())
        ),
        asyncInvokerManager = AsyncInvokerManager(
            parameterInstances = mapOf(),
            invokeAdapters = kotlin.run {
                val tempLifecycle = lifecycle
                val tempLogger = InvokeAdapterInitializer.logger()
                InvokeAdapterInitializer.factories()
                    .map {
                        it.create(tempLifecycle, tempLogger)
                    }
            }
        ))

    private val lifecycleNotifier: LifecycleNotifier = LifecycleNotifierImpl(invokeManager)

    override fun onCreate() {
        super.onCreate()
        serviceInit()
        lifecycleNotifier.run {
            triggerCreated()
            triggerStarted()
            triggerResumed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleNotifier.run {
            triggerPause()
            triggerStop()
            triggerDestroy()
        }
    }

    override fun add(listener: LifecycleListener) {
        lifecycleNotifier.add(listener)
    }

    override fun remove(listener: LifecycleListener) {
        lifecycleNotifier.remove(listener)
    }

    abstract fun serviceInit()
}