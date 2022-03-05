package io.androidalatan.lifecycle.handler.invokeradapter.api

import androidx.lifecycle.Lifecycle
import java.lang.reflect.Type

interface InvokeAdapter<RETURN> {

    fun acceptableInvoke(returnType: Type): Boolean
    fun convertInvoke(body: () -> Any): RETURN

    fun acceptableRevoke(invoked: Any): Boolean
    fun convertRevoke(invoked: Any)

    interface Factory<T> {
        fun create(lifecycle: Lifecycle, logger: ErrorLogger): InvokeAdapter<T>
    }
}