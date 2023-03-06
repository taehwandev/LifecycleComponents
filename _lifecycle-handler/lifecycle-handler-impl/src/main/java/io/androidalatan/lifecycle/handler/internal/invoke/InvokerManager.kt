package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo

interface InvokerManager {
    fun addMethods(caller: Any, lifecycleListener: LifecycleListener, methods: List<MethodInfo>)
    fun removeMethodsOf(caller: Any, lifecycleListener: LifecycleListener)
    fun execute(caller: Any, currentStatus: LifecycleStatus)
    fun executeMissingEvent(caller: Any, lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus)
}