package io.androidalatan.lifecycle.handler.invokeradapter.test

import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer
import io.androidalatan.lifecycle.handler.invokeradapter.flow.FlowInvokeAdapter

fun invokeAdapterInitializer() {
    InvokeAdapterInitializer.initialize(
        factories = listOf(FlowInvokeAdapter.Factory()),
        errorLogger = { }
    )
}