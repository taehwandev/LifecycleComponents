package io.androidalatan.lifecycle.handler.activity.di

import dagger.BindsInstance
import io.androidalatan.dagger.builder.BaseServiceBuilder
import io.androidalatan.lifecycle.handler.activity.LifecycleService
import io.androidalatan.lifecycle.handler.api.LifecycleSource

abstract class LifecycleServiceBuilder<T : LifecycleService> : BaseServiceBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleService(instance)
    }

    @BindsInstance
    abstract fun seedLifecycleService(instance: LifecycleService)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: LifecycleSource)

}