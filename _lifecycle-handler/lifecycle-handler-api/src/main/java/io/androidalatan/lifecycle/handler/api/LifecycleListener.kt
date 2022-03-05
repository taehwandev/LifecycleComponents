package io.androidalatan.lifecycle.handler.api

open class LifecycleListener(lifecycleSource: LifecycleSource, autoActivate: Boolean = true) {
    init {
        if (autoActivate) {
            lifecycleSource.add(this)
        }
    }
}