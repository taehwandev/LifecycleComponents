package io.androidalatan.lifecycle.handler.activity

import androidx.annotation.VisibleForTesting
import io.androidalatan.lifecycle.handler.internal.ListenerAnalyzer
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManager
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier
import io.androidalatan.lifecycle.handler.api.LifecycleSource

class LifecycleNotifierImpl(private val invokerManager: InvokerManager) : LifecycleNotifier {

    private val lifecycleListenerAnalyzer = ListenerAnalyzer()

    private var currentStatus = LifecycleStatus.UNKNOWN

    @VisibleForTesting
    internal val cachedListeners = mutableSetOf<LifecycleListener>()

    override fun add(lifecycleSource: LifecycleSource, lifecycleListener: LifecycleListener) {
        synchronized(this) {
            if (cachedListeners.contains(lifecycleListener)) return@synchronized
            cachedListeners.add(lifecycleListener)
            val methods = lifecycleListenerAnalyzer.analyze(lifecycleListener)
            invokerManager.addMethods(lifecycleSource, lifecycleListener, methods.toList())

            when (currentStatus) {
                LifecycleStatus.ON_CREATED, LifecycleStatus.ON_STOP -> listOf(LifecycleStatus.ON_CREATED)
                LifecycleStatus.ON_STARTED, LifecycleStatus.ON_PAUSE -> listOf(
                    LifecycleStatus.ON_CREATED,
                    LifecycleStatus.ON_STARTED
                )
                LifecycleStatus.ON_RESUMED -> listOf(
                    LifecycleStatus.ON_CREATED,
                    LifecycleStatus.ON_STARTED,
                    LifecycleStatus.ON_RESUMED
                )
                else -> emptyList()
            }
                .forEach {
                    invokerManager.executeMissingEvent(lifecycleSource, lifecycleListener, it)
                }
        }
    }

    override fun remove(lifecycleSource: LifecycleSource, lifecycleListener: LifecycleListener) {
        synchronized(this) {
            if (!cachedListeners.contains(lifecycleListener)) return@synchronized
            cachedListeners.remove(lifecycleListener)

            when(currentStatus) {
                LifecycleStatus.ON_CREATED, LifecycleStatus.ON_STOP -> listOf(LifecycleStatus.ON_DESTROY)
                LifecycleStatus.ON_STARTED, LifecycleStatus.ON_PAUSE -> listOf(
                    LifecycleStatus.ON_STOP,
                    LifecycleStatus.ON_DESTROY
                )
                LifecycleStatus.ON_RESUMED -> listOf(
                    LifecycleStatus.ON_PAUSE,
                    LifecycleStatus.ON_STOP,
                    LifecycleStatus.ON_DESTROY
                )
                else -> emptyList()
            }
                .forEach {
                    invokerManager.executeMissingEvent(lifecycleSource, lifecycleListener, it)
                }

            invokerManager.removeMethodsOf(lifecycleSource, lifecycleListener)
        }
    }

    override fun triggerCreated(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_CREATED
            executeInvokeManager(lifecycleSource)
        }
    }

    private fun executeInvokeManager(lifecycleSource: LifecycleSource) {
        invokerManager.execute(lifecycleSource, currentStatus)
    }

    override fun triggerStarted(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_STARTED
            executeInvokeManager(lifecycleSource)
        }
    }

    override fun triggerResumed(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_RESUMED
            executeInvokeManager(lifecycleSource)
        }
    }

    override fun triggerPause(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_PAUSE
            executeInvokeManager(lifecycleSource)
        }
    }

    override fun triggerStop(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_STOP
            executeInvokeManager(lifecycleSource)
        }
    }

    override fun triggerDestroy(lifecycleSource: LifecycleSource) {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_DESTROY
            executeInvokeManager(lifecycleSource)
        }
    }
}