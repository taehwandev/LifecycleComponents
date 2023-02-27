package io.androidalatan.compose.holder

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.compose.holder.lifecycle.activator.LifecycleActivator
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleListener

abstract class ComposableHolder(private val componentBuilder: ComposableHolderComponent.Builder<*>) : ChildLifecycleSource {

    private var lock: Any = UNINITIALIZED

    private val lifecycleActivator = LifecycleActivator()

    protected open val activateAllListeners: Boolean = true

    // inspired by dagger.internal.DoubleCheck
    private fun injectIfNeeded() {
        var result = lock
        if (result == UNINITIALIZED) {
            synchronized(this) {
                result = lock
                if (result == UNINITIALIZED) {
                    (componentBuilder.lifecycleSource(this)
                        .build() as ComposableHolderComponent<ComposableHolder>)
                        .inject(this)
                    lock = true
                }
            }
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun showContent() {

        var initialized by remember { mutableStateOf(lock) }

        if (initialized == UNINITIALIZED) {
            LaunchedEffect(key1 = initialized) {
                injectIfNeeded()
                initialized = lock
            }
        } else {
            lifecycleActivator.activateAllIfNeeded(activateWhenInit = activateAllListeners)
            render()
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    abstract fun render()

    companion object {
        private val UNINITIALIZED = Any()

    }

    override fun add(listener: LifecycleListener) {
        lifecycleActivator.add(listener)
    }

    override fun remove(listener: LifecycleListener) {
        lifecycleActivator.remove(listener)
    }
}