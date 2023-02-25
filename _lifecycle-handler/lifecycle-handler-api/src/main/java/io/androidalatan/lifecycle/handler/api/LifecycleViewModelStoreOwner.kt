package io.androidalatan.lifecycle.handler.api

interface LifecycleViewModelStoreOwner {

    fun getLifecycleListener(key: String?): LifecycleListener

    fun cached(lifecycleListener: LifecycleListener)

    fun remove(lifecycleListener: LifecycleListener)
}