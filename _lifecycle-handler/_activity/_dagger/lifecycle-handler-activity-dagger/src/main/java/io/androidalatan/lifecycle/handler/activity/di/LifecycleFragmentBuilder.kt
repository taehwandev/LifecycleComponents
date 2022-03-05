package io.androidalatan.lifecycle.handler.activity.di

import dagger.BindsInstance
import io.androidalatan.dagger.builder.BaseFragmentBuilder
import io.androidalatan.lifecycle.handler.activity.LifecycleFragment
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource

abstract class LifecycleFragmentBuilder<T : LifecycleFragment> : BaseFragmentBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleFragment(instance)
    }

    @BindsInstance
    abstract fun seedLifecycleFragment(instance: LifecycleFragment)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: ChildLifecycleSource)

}