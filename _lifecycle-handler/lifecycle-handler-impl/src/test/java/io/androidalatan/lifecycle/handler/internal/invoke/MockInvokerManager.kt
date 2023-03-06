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
    override fun addMethods(
        caller: Any,
        lifecycleListener: LifecycleListener,
        methods: List<MethodInfo>
    ) {
        addMethodCount++
    }

    override fun removeMethodsOf(caller: Any, lifecycleListener: LifecycleListener) {
        removeMethodCount++
    }

    override fun execute(caller: Any, currentStatus: LifecycleStatus) {
        executeCount++
    }

    fun reset() {
        addMethodCount = 0
        removeMethodCount = 0
        executeCount = 0
    }

    override fun executeMissingEvent(
        caller: Any,
        lifecycleListener: LifecycleListener,
        currentStatus: LifecycleStatus
    ) {
        executeMissingCount++
        lastLifecycleListener = lifecycleListener
        lastExecutedStatus = currentStatus
    }
}