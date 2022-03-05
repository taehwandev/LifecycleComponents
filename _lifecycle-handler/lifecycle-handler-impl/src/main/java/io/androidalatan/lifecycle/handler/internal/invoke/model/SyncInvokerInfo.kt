package io.androidalatan.lifecycle.handler.internal.invoke.model

import java.lang.reflect.Method

data class SyncInvokerInfo(
    val method: Method,
    val invokeMethodCoroutine: Boolean = false,
    val launchDispatcherUI: Boolean = false,
)