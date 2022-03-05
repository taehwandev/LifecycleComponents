package io.androidalatan.lifecycle.handler.internal.invoke.model

import java.lang.reflect.Method

data class InvokeInfo(val method: Method, val parameters: List<Any> = emptyList())