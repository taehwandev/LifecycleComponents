package io.androidalatan.lifecycle.handler.invokeradapter.api

object InvokeAdapterInitializer {
    private val factories = mutableListOf<InvokeAdapter.Factory<*>>()
    private var logger: ErrorLogger = ErrorLogger {}

    private var initialized = false

    fun initialize(factories: List<InvokeAdapter.Factory<*>>, errorLogger: ErrorLogger): InvokeAdapterInitializer {
        if (initialized) throw IllegalStateException("InvokeAdapterInitializer was initialized before.")
        this.factories.addAll(factories)
        this.logger = errorLogger
        initialized = true
        return this
    }

    fun logger(): ErrorLogger = logger

    fun factories(): List<InvokeAdapter.Factory<*>> = factories
}