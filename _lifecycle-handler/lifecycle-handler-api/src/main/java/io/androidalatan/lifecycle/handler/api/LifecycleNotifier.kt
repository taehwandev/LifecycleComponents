package io.androidalatan.lifecycle.handler.api

interface LifecycleNotifier {

    fun add(lifecycleListener: LifecycleListener)
    fun remove(lifecycleListener: LifecycleListener)

    fun triggerCreated()
    fun triggerStarted()
    fun triggerResumed()
    fun triggerPause()
    fun triggerStop()
    fun triggerDestroy()
}