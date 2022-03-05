package io.androidalatan.lifecycle.handler.invokeradapter.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import io.androidalatan.lifecycle.handler.invokeradapter.api.ErrorLogger
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import java.lang.reflect.Type

class FlowInvokeAdapter(
    private val coroutineScope: CoroutineScope,
    private val logger: ErrorLogger
) : InvokeAdapter<Job> {

    override fun acceptableInvoke(returnType: Type): Boolean {
        return returnType in INVOKE_TYPE
    }

    override fun convertInvoke(body: () -> Any): Job {
        return (body() as Flow<*>)
            .catch { logger.e(it) }
            .launchIn(coroutineScope)
    }

    override fun acceptableRevoke(invoked: Any): Boolean {
        return invoked is Job
    }

    override fun convertRevoke(invoked: Any) {
        (invoked as? Job)
            ?.let { job ->
                if (job.isActive) {
                    job.cancel()
                }
            }
    }

    companion object {
        private val INVOKE_TYPE =
            hashSetOf<Type>(Flow::class.java)
    }

    class Factory : InvokeAdapter.Factory<Job> {
        override fun create(lifecycle: Lifecycle, logger: ErrorLogger): InvokeAdapter<Job> {
            return FlowInvokeAdapter(lifecycle.coroutineScope, logger)
        }
    }
}