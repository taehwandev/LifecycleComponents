package io.androidalatan.lifecycle.handler.api

interface LifecycleNotifier {

    fun add(lifecycleSource: LifecycleSource, lifecycleListener: LifecycleListener)
    fun remove(lifecycleSource: LifecycleSource, lifecycleListener: LifecycleListener)

    fun triggerCreated(lifecycleSource: LifecycleSource)
    fun triggerStarted(lifecycleSource: LifecycleSource)
    fun triggerResumed(lifecycleSource: LifecycleSource)
    fun triggerPause(lifecycleSource: LifecycleSource)
    fun triggerStop(lifecycleSource: LifecycleSource)
    fun triggerDestroy(lifecycleSource: LifecycleSource)
}