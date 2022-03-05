package io.androidalatan.lifecycle.handler.internal.invoke.coroutine

import androidx.annotation.VisibleForTesting
import io.androidalatan.coroutine.api.coroutineScope
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import java.lang.reflect.Method
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutineInvokerManagerImpl(
    private val dispatcherProvider: DispatcherProvider,
) : CoroutineInvokerManager {

    @VisibleForTesting
    internal val coroutineScope: CoroutineScope = dispatcherProvider.coroutineScope

    override fun invokeSuspendMethod(method: Method, instance: LifecycleListener, launchDispatcherUI: Boolean) {
        val dispatcher = if (launchDispatcherUI) {
            dispatcherProvider.main()
        } else {
            dispatcherProvider.default()
        }

        coroutineScope.launch(dispatcher + CoroutineName(method.name)) {
            suspendCoroutine<Any> {
                it.resume(method.invoke(instance, it)!!)
            }
        }
    }

    companion object {
        private val DispatcherProvider.coroutineScope: CoroutineScope
            get() = object : CoroutineScope {
                override val coroutineContext: CoroutineContext
                    get() = this@coroutineScope.default() + SupervisorJob()
            }
    }
}