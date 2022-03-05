package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleConstant
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo

class InvokerManagerImpl(
    private val asyncInvokerManager: InvokerManager,
    private val syncInvokerManager: InvokerManager
) : InvokerManager {
    override fun addMethods(lifecycleListener: LifecycleListener, methods: List<MethodInfo>) {
        methods.groupBy {
            if (it.annotationKClass in LifecycleConstant.GROUP_OF_ASYNC_ANNOTATION) {
                KEY_ASYNC
            } else {
                KEY_SYNC
            }
        }
            .forEach { methodGroup ->
                if (methodGroup.key == KEY_ASYNC) {
                    asyncInvokerManager.addMethods(lifecycleListener, methodGroup.value)
                } else {
                    syncInvokerManager.addMethods(lifecycleListener, methodGroup.value)
                }
            }
    }

    override fun removeMethodsOf(lifecycleListener: LifecycleListener) {
        asyncInvokerManager.removeMethodsOf(lifecycleListener)
        syncInvokerManager.removeMethodsOf(lifecycleListener)
    }

    override fun executeMissingEvent(lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus) {
        asyncInvokerManager.executeMissingEvent(lifecycleListener, currentStatus)
        syncInvokerManager.executeMissingEvent(lifecycleListener, currentStatus)
    }

    override fun execute(currentStatus: LifecycleStatus) {
        asyncInvokerManager.execute(currentStatus)
        syncInvokerManager.execute(currentStatus)
    }

    companion object {
        private const val KEY_ASYNC = 1
        private const val KEY_SYNC = 2
    }
}