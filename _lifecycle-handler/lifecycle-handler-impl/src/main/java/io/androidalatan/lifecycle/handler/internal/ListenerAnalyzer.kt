package io.androidalatan.lifecycle.handler.internal

import io.androidalatan.lifecycle.handler.internal.model.LifecycleConstant
import io.androidalatan.lifecycle.handler.internal.model.MethodInfo
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import java.lang.reflect.Modifier

class ListenerAnalyzer {

    val methodCache = ListenerMethodCache()

    fun analyze(lifecycleListener: LifecycleListener): Set<MethodInfo> {
        if (lifecycleListener::class !in methodCache) {
            lifecycleListener.javaClass.declaredMethods
                .filter { Modifier.isPublic(it.modifiers) }
                .mapNotNull { method ->
                    method.declaredAnnotations.firstOrNull { isSupportedAnnotation(it) }
                        ?.let { annotation ->
                            MethodInfo(annotation.annotationClass, method)
                        }
                }
                .takeIf { it.isNotEmpty() }
                ?.toHashSet()
                ?.let { methodCache.add(lifecycleListener::class, it) }
        }

        return methodCache[lifecycleListener::class]
    }

    private fun isSupportedAnnotation(annotation: Annotation): Boolean {
        val kClass = annotation.annotationClass
        return (kClass in LifecycleConstant.GROUP_OF_ASYNC_ANNOTATION) or (kClass in LifecycleConstant.GROUP_OF_SYNC_ANNOTATION)
    }
}