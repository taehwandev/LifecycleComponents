package io.androidalatan.compose.holder.lifecycle.activator

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.compose.util.activate
import kotlin.reflect.KClass

class LifecycleActivator {

    @VisibleForTesting
    internal val cachedLifecycleListener = hashMapOf<KClass<out LifecycleListener>, LifecycleListener>()

    fun add(listener: LifecycleListener) {
        cachedLifecycleListener[listener::class] = listener
    }

    fun remove(listener: LifecycleListener) {
        cachedLifecycleListener.remove(listener::class)
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun activateAllIfNeeded(activateWhenInit: Boolean) {
        val listeners = remember { cachedLifecycleListener }
        if (activateWhenInit) {
            listeners.values.forEach {
                it.activate()
            }
        }
    }
}