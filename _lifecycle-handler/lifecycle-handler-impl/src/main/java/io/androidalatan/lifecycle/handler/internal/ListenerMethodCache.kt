package io.androidalatan.lifecycle.handler.internal

import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import kotlin.reflect.KClass

class ListenerMethodCache {
    val invokerInfo = hashMapOf<KClass<out LifecycleListener>, Set<MethodInfo>>()

    fun add(lifecycleClass: KClass<out LifecycleListener>, methodInfo: Set<MethodInfo>) {
        invokerInfo[lifecycleClass] = methodInfo
    }

    operator fun contains(lifecycleClass: KClass<out LifecycleListener>): Boolean = invokerInfo.containsKey(lifecycleClass)

    operator fun get(lifecycleClass: KClass<out LifecycleListener>): Set<MethodInfo> = invokerInfo[lifecycleClass] ?: emptySet()
}