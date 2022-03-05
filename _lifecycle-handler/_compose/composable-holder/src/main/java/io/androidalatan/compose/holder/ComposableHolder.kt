package io.androidalatan.compose.holder

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.compose.holder.lifecycle.activator.LifecycleActivator
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleListener

abstract class ComposableHolder(private val componentBuilder: ComposableHolderComponent.Builder<*>) : ChildLifecycleSource {

    private var lock: Any = UNINITIALIZED

    private val lifecycleActivator = LifecycleActivator()

    protected open val activateAllListeners: Boolean = false

    // inspired by dagger.internal.DoubleCheck
    fun injectIfNeeded() {
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
        lifecycleActivator.activateAllIfNeeded(activateWhenInit = activateAllListeners)
        render()
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