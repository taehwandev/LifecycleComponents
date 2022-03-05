package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo

interface InvokerManager {
    fun addMethods(lifecycleListener: LifecycleListener, methods: List<MethodInfo>)
    fun removeMethodsOf(lifecycleListener: LifecycleListener)
    fun execute(currentStatus: LifecycleStatus)
    fun executeMissingEvent(lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus)
}