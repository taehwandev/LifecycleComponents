package io.androidalatan.coroutine.api

import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val DispatcherProvider.coroutineScope: CoroutineScope
    get() = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = this@coroutineScope.default() + SupervisorJob()
    }