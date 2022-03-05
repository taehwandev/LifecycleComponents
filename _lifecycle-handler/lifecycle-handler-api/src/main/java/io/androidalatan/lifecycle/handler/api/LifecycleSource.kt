package io.androidalatan.lifecycle.handler.api

interface LifecycleSource {
    fun add(listener: LifecycleListener) {}
    fun remove(listener: LifecycleListener) {}
}