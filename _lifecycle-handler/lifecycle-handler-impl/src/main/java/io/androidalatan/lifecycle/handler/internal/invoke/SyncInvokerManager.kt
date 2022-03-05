package io.androidalatan.lifecycle.handler.internal.invoke

import android.util.Log
import io.androidalatan.coroutine.handler.annotations.RunDispatcherUI
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.annotations.sync.OnDestroy
import io.androidalatan.lifecycle.handler.annotations.sync.OnPause
import io.androidalatan.lifecycle.handler.annotations.sync.OnResumed
import io.androidalatan.lifecycle.handler.annotations.sync.OnStarted
import io.androidalatan.lifecycle.handler.annotations.sync.OnStop
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.internal.invoke.coroutine.CoroutineInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.model.SyncInvokerInfo
import io.androidalatan.lifecycle.handler.internal.model.LifecycleStatus
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import kotlin.coroutines.Continuation
import kotlin.reflect.KClass

class SyncInvokerManager(
    private val coroutineInvokerManager: CoroutineInvokerManager,
) : InvokerManager {
    val invokers = hashMapOf<LifecycleListener, Map<KClass<out Annotation>, List<SyncInvokerInfo>>>()

    override fun addMethods(lifecycleListener: LifecycleListener, methods: List<MethodInfo>) {
        invokers[lifecycleListener] = methods.groupBy(
            { it.annotationKClass },
            {
                SyncInvokerInfo(
                    method = it.method,
                    invokeMethodCoroutine = it.method.parameterTypes.any { type ->
                        type == Continuation::class.java
                    },
                    launchDispatcherUI = it.method.declaredAnnotations.any { annotation ->
                        annotation.annotationClass == RunDispatcherUI::class
                    }
                )
            })
    }

    override fun removeMethodsOf(lifecycleListener: LifecycleListener) {
        invokers.remove(lifecycleListener)
    }

    override fun execute(currentStatus: LifecycleStatus) {
        if (currentStatus == LifecycleStatus.UNKNOWN) {
            return
        }

        val mappedAnnotation = convert(currentStatus)
        invokers.filter { invokeInfo ->
            invokeInfo.value[mappedAnnotation].isNullOrEmpty()
                .not()
        }
            .forEach { (instance, methods) ->
                invokeMethods(methods, mappedAnnotation, instance)
            }
    }

    override fun executeMissingEvent(lifecycleListener: LifecycleListener, currentStatus: LifecycleStatus) {
        val annotationKlass = convert(currentStatus) ?: return
        invokers.filter { (_lifecycleListener, methods) ->
            lifecycleListener == _lifecycleListener && methods[annotationKlass].isNullOrEmpty()
                .not()
        }
            .forEach { (instance, methods) ->
                invokeMethods(methods, annotationKlass, instance)
            }
    }

    private fun invokeMethods(
        methods: Map<KClass<out Annotation>, List<SyncInvokerInfo>>,
        mappedAnnotation: KClass<out Annotation>?,
        instance: LifecycleListener
    ) {
        methods[mappedAnnotation]?.forEach { (method, invokerCoroutines, launchDispatcherUI) ->
            try {
                when {
                    invokerCoroutines -> {
                        coroutineInvokerManager.invokeSuspendMethod(method, instance, launchDispatcherUI)
                    }
                    else -> {
                        method.invoke(instance)
                    }
                }
            } catch (t: Throwable) {
                Log.e("SyncInvokerManager", "It is occurred error.", t)
            }
        }
    }

    private fun convert(currentStatus: LifecycleStatus): KClass<out Annotation>? {
        return when (currentStatus) {
            LifecycleStatus.UNKNOWN -> null
            LifecycleStatus.ON_CREATED -> OnCreated::class
            LifecycleStatus.ON_STARTED -> OnStarted::class
            LifecycleStatus.ON_RESUMED -> OnResumed::class
            LifecycleStatus.ON_PAUSE -> OnPause::class
            LifecycleStatus.ON_STOP -> OnStop::class
            LifecycleStatus.ON_DESTROY -> OnDestroy::class
        }
    }
}