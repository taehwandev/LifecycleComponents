package io.androidalatan.lifecycle.handler.internal.model

import java.lang.reflect.Method
import kotlin.reflect.KClass

data class MethodInfo(
    val annotationKClass: KClass<out Annotation>, val method: Method,
)