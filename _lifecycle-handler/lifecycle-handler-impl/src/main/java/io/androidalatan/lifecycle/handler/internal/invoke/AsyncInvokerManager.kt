package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.invoke.model.InvokeInfo
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapter
import kotlin.reflect.KClass

class AsyncInvokerManager(
    private val parameterInstances: Map<Class<*>, Any> = mapOf(),
    private val invokeAdapters: List<InvokeAdapter<*>>,
) : InvokerManager {

    val invokers = hashMapOf<LifecycleListener, MutableMap<KClass<out Annotation>, MutableList<InvokeInfo>>>()

    val invokedMap = hashMapOf<LifecycleListener, MutableMap<KClass<out Annotation>, MutableList<Any>>>()

    override fun addMethods(lifecycleListener: LifecycleListener, methods: List<MethodInfo>) {
        invokers[lifecycleListener] = methods
            .map { (annotation, method) ->
                val parameters = method.parameterTypes.mapNotNull {
                    parameterInstances[it] ?: throw throw IllegalArgumentException("${it.simpleName} is not supported parameter")
                }

                annotation to InvokeInfo(method, parameters)
            }
            .groupByTo(mutableMapOf(), { it.first }, { it.second })
    }

    override fun removeMethodsOf(lifecycleListener: LifecycleListener) {
        invokers[lifecycleListener]?.forEach { invoker ->
            invoker.value.clear()
        }
        invokers[lifecycleListener]?.clear()
        invokers.remove(lifecycleListener)
    }

    override fun execute(currentStatus: LifecycleStatus) {
        when (currentStatus) {
            LifecycleStatus.UNKNOWN -> {
                // do nothing
            }
            LifecycleStatus.ON_CREATED -> invokeMethod(CreatedToDestroy::class)
            LifecycleStatus.ON_STARTED -> invokeMethod(StartedToStop::class)
            LifecycleStatus.ON_RESUMED -> invokeMethod(ResumedToPause::class)
            LifecycleStatus.ON_PAUSE -> revokeMethod(ResumedToPause::class)
            LifecycleStatus.ON_STOP -> revokeMethod(StartedToStop::class)
            LifecycleStatus.ON_DESTROY -> {
                revokeMethod(CreatedToDestroy::class)
                clearCachedMethod()
            }
        }
    }

    override fun executeMissingEvent(lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus) {

        if (currentStatus == LifecycleStatus.UNKNOWN) return

        when (currentStatus) {
            LifecycleStatus.ON_CREATED -> true to CreatedToDestroy::class
            LifecycleStatus.ON_STARTED -> true to StartedToStop::class
            LifecycleStatus.ON_RESUMED -> true to ResumedToPause::class
            LifecycleStatus.ON_PAUSE -> false to ResumedToPause::class
            LifecycleStatus.ON_STOP -> false to StartedToStop::class
            LifecycleStatus.ON_DESTROY -> false to CreatedToDestroy::class
            else -> null
        }?.let { (invoke, annotationKlass) ->
            if (invoke) {
                invokers.filter { (listener, _) -> listener == lifecycleListener }
                    .forEach { (lifecycleListener, methods) ->
                        executeLifecycleListener(annotationKlass, methods, lifecycleListener)
                    }
            } else {
                invokedMap[lifecycleListener]?.get(annotationKlass)
                    ?.let { invokedList ->
                        invokedList.forEach { invoked ->
                            invokeAdapters.firstOrNull { it.acceptableRevoke(invoked) }
                                ?.convertRevoke(invoked)
                        }
                        invokedList.clear()
                    }
            }
        }
    }

    private fun invokeMethod(annotationKlass: KClass<out Annotation>) {
        invokers.forEach { (lifecycleListener, methods) ->
            executeLifecycleListener(annotationKlass, methods, lifecycleListener)
        }
    }

    private fun executeLifecycleListener(
        annotationKClass: KClass<out Annotation>,
        methods: Map<KClass<out Annotation>, List<InvokeInfo>>,
        lifecycleListener: LifecycleListener
    ) {
        val invokedList = invokedMap.getOrPut(lifecycleListener) { hashMapOf() }
            .getOrPut(annotationKClass) { mutableListOf() }
        methods[annotationKClass]?.forEach { invokeInfo ->
            val returnType = invokeInfo.method.returnType
            invokeAdapters.firstOrNull { it.acceptableInvoke(returnType) }
                ?.convertInvoke {
                    if (invokeInfo.parameters.isNullOrEmpty()) {
                        invokeInfo.method.invoke(lifecycleListener) ?: Unit
                    } else {
                        invokeInfo.method.invoke(lifecycleListener, *invokeInfo.parameters.toTypedArray()) ?: Unit
                    }
                }
                ?.let {
                    invokedList.add(it)
                }
        }
    }

    private fun revokeMethod(annotationKClass: KClass<out Annotation>) {
        invokedMap.values.map {
            it[annotationKClass] ?: mutableListOf()
        }
            .forEach { invokedList ->
                invokedList.forEach { invoked ->
                    invokeAdapters.firstOrNull { it.acceptableRevoke(invoked) }
                        ?.convertRevoke(invoked)
                }
                invokedList.clear()
            }
    }

    private fun clearCachedMethod() {
        invokedMap.values.forEach { methodCache ->
            methodCache.values.forEach { invokedMethod -> invokedMethod.clear() }
            methodCache.clear()
        }
        invokedMap.clear()
    }
}