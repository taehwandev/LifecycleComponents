package io.androidalatan.compose.holder

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.compose.activator.LifecycleActivator
import io.androidalatan.lifecycle.handler.compose.cache.ComposeCacheProvider
import io.androidalatan.lifecycle.handler.compose.cache.LocalComposeComposeCacheOwner
import io.androidalatan.lifecycle.handler.compose.cache.composeCacheProvider
import io.androidalatan.lifecycle.handler.compose.util.LifecycleHandle
import io.androidalatan.lifecycle.handler.compose.util.LocalLifecycleNotifierOwner
import io.androidalatan.lifecycle.handler.compose.util.LocalLifecycleSourceOwner

abstract class ComposableHolder(
    private val componentBuilder: ComposableHolderComponent.Builder<*>
) : ChildLifecycleSource, ComposeCacheProvider by composeCacheProvider() {

    private var lock: Any = UNINITIALIZED

    private val lifecycleActivator = LifecycleActivator()

    private val composeCache by lazy { composeCache() }

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
            CompositionLocalProvider(
                LocalComposeComposeCacheOwner provides composeCache,
                LocalLifecycleSourceOwner provides this@ComposableHolder
            ) {
                val lifecycleOwner = LocalLifecycleOwner.current
                val lifecycleNotifier = LocalLifecycleNotifierOwner.current
                val lifecycleSource = LocalLifecycleSourceOwner.current
                DisposableEffect(key1 = lifecycleOwner, lifecycleNotifier, lifecycleSource) {
                    var observer: LifecycleObserver? = null
                    observer = LifecycleEventObserver { source, event ->
                        if (event == Lifecycle.Event.ON_DESTROY) {
                            observer?.let { lifecycleOwner.lifecycle.removeObserver(it) }
                            composeCache.clear()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                        when (lifecycleOwner.lifecycle.currentState) {
                            Lifecycle.State.CREATED -> lifecycleNotifier.triggerDestroy(lifecycleSource)
                            Lifecycle.State.STARTED -> {
                                lifecycleNotifier.triggerStop(lifecycleSource)
                                lifecycleNotifier.triggerDestroy(lifecycleSource)
                            }

                            Lifecycle.State.RESUMED -> {
                                lifecycleNotifier.triggerPause(lifecycleSource)
                                lifecycleNotifier.triggerStop(lifecycleSource)
                                lifecycleNotifier.triggerDestroy(lifecycleSource)
                            }

                            else -> {
                            /*do nothing*/
                            }
                        }
                    }
                }

                LifecycleHandle {
                    lifecycleActivator.activateAllIfNeeded(activateWhenInit = activateAllListeners)
                    render()
                }
            }
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