package io.androidalatan.compose.holder.di

import dagger.BindsInstance
import io.androidalatan.compose.holder.ComposableHolder
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource

interface ComposableHolderComponent<Injected : ComposableHolder> {
    fun inject(instance: Injected)

    interface Builder<Component : ComposableHolderComponent<*>> {
        @BindsInstance
        fun lifecycleSource(lifecycleSource: ChildLifecycleSource): Builder<Component>
        fun build(): Component
    }
}