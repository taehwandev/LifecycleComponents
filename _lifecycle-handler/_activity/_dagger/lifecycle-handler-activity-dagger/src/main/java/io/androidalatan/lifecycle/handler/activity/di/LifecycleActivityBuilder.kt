package io.androidalatan.lifecycle.handler.activity.di

import dagger.BindsInstance
import io.androidalatan.dagger.builder.BaseActivityBuilder
import io.androidalatan.lifecycle.handler.activity.LifecycleActivity
import io.androidalatan.lifecycle.handler.api.LifecycleSource

abstract class LifecycleActivityBuilder<T : LifecycleActivity> : BaseActivityBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleActivity(instance)
    }

    @BindsInstance
    abstract fun seedLifecycleActivity(instance: LifecycleActivity)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: LifecycleSource)

}