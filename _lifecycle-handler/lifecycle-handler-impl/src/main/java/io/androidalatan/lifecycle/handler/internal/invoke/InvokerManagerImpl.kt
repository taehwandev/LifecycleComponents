package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleConstant
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo

class InvokerManagerImpl(
    private val asyncInvokerManager: InvokerManager,
    private val syncInvokerManager: InvokerManager
) : InvokerManager {
    override fun addMethods(caller: Any, lifecycleListener: LifecycleListener, methods: List<MethodInfo>) {
        methods.groupBy {
            if (it.annotationKClass in LifecycleConstant.GROUP_OF_ASYNC_ANNOTATION) {
                KEY_ASYNC
            } else {
                KEY_SYNC
            }
        }
            .forEach { methodGroup ->
                if (methodGroup.key == KEY_ASYNC) {
                    asyncInvokerManager.addMethods(caller, lifecycleListener, methodGroup.value)
                } else {
                    syncInvokerManager.addMethods(caller, lifecycleListener, methodGroup.value)
                }
            }
    }

    override fun removeMethodsOf(caller: Any, lifecycleListener: LifecycleListener) {
        asyncInvokerManager.removeMethodsOf(caller, lifecycleListener)
        syncInvokerManager.removeMethodsOf(caller, lifecycleListener)
    }

    override fun executeMissingEvent(caller: Any, lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus) {
        asyncInvokerManager.executeMissingEvent(caller, lifecycleListener, currentStatus)
        syncInvokerManager.executeMissingEvent(caller, lifecycleListener, currentStatus)
    }

    override fun execute(caller: Any, currentStatus: LifecycleStatus) {
        asyncInvokerManager.execute(caller, currentStatus)
        syncInvokerManager.execute(caller, currentStatus)
    }

    companion object {
        private const val KEY_ASYNC = 1
        private const val KEY_SYNC = 2
    }
}