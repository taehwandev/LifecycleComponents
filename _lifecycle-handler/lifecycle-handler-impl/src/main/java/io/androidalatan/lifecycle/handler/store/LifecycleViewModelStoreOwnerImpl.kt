package io.androidalatan.lifecycle.handler.store

import androidx.annotation.VisibleForTesting
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleViewModelStoreOwner

class LifecycleViewModelStoreOwnerImpl : LifecycleViewModelStoreOwner {

    @VisibleForTesting
    val cached = mutableMapOf<String, LifecycleListener>()

    override fun getLifecycleListener(key: String?): LifecycleListener =
        synchronized(this) {
            cached[key] ?: throw Exception("Not found $key. call $key.activate()")
        }

    override fun cached(lifecycleListener: LifecycleListener) {
        val key = lifecycleListener::class.qualifiedName
        if (key != null && !cached.containsKey(key)) {
            synchronized(this) {
                if (!cached.containsKey(key)) {
                    cached[key] = lifecycleListener
                }
            }
        }
    }

    override fun remove(lifecycleListener: LifecycleListener) {
        val key = lifecycleListener::class.qualifiedName
        if (key != null && cached.containsKey(key)) {
            synchronized(this) {
                if (cached.containsKey(key)) {
                    cached.remove(key)
                }
            }
        }
    }
}