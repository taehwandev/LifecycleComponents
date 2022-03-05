package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo

class MockInvokerManager : InvokerManager {
    var addMethodCount = 0
    var removeMethodCount = 0
    var executeCount = 0
    var executeMissingCount = 0
    var lastLifecycleListener: LifecycleListener? = null
    var lastExecutedStatus: LifecycleStatus? = null
    override fun addMethods(lifecycleListener: LifecycleListener, methods: List<MethodInfo>) {
        addMethodCount++
    }

    override fun removeMethodsOf(lifecycleListener: LifecycleListener) {
        removeMethodCount++
    }

    override fun execute(currentStatus: LifecycleStatus) {
        executeCount++
    }

    fun reset() {
        addMethodCount = 0
        removeMethodCount = 0
        executeCount = 0
    }

    override fun executeMissingEvent(lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus) {
        executeMissingCount++
        lastLifecycleListener = lifecycleListener
        lastExecutedStatus = currentStatus
    }
}