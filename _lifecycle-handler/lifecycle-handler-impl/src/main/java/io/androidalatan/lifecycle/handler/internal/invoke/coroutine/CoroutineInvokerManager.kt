package io.androidalatan.lifecycle.handler.internal.invoke.coroutine

import io.androidalatan.lifecycle.handler.api.LifecycleListener
import java.lang.reflect.Method

interface CoroutineInvokerManager {

    fun invokeSuspendMethod(
        method: Method,
        instance: LifecycleListener,
        launchDispatcherUI: Boolean,
    )
}