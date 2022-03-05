package io.androidalatan.lifecycle.handler.activity

import androidx.annotation.VisibleForTesting
import io.androidalatan.lifecycle.handler.internal.ListenerAnalyzer
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManager
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier

class LifecycleNotifierImpl(private val invokerManager: InvokerManager) : LifecycleNotifier {

    private val lifecycleListenerAnalyzer = ListenerAnalyzer()

    private var currentStatus = LifecycleStatus.UNKNOWN

    @VisibleForTesting
    internal val cachedListeners = mutableSetOf<LifecycleListener>()

    override fun add(lifecycleListener: LifecycleListener) {
        synchronized(this) {
            if (cachedListeners.contains(lifecycleListener)) return@synchronized
            cachedListeners.add(lifecycleListener)
            val methods = lifecycleListenerAnalyzer.analyze(lifecycleListener)
            invokerManager.addMethods(lifecycleListener, methods.toList())

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
                    invokerManager.executeMissingEvent(lifecycleListener, it)
                }
        }
    }

    override fun remove(lifecycleListener: LifecycleListener) {
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
                    invokerManager.executeMissingEvent(lifecycleListener, it)
                }

            invokerManager.removeMethodsOf(lifecycleListener)
        }
    }

    override fun triggerCreated() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_CREATED
            executeInvokeManager()
        }
    }

    private fun executeInvokeManager() {
        invokerManager.execute(currentStatus)
    }

    override fun triggerStarted() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_STARTED
            executeInvokeManager()
        }
    }

    override fun triggerResumed() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_RESUMED
            executeInvokeManager()
        }
    }

    override fun triggerPause() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_PAUSE
            executeInvokeManager()
        }
    }

    override fun triggerStop() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_STOP
            executeInvokeManager()
        }
    }

    override fun triggerDestroy() {
        synchronized(this) {
            currentStatus = LifecycleStatus.ON_DESTROY
            executeInvokeManager()
        }
    }
}