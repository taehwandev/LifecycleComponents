package io.androidalatan.dagger.builder

import android.app.Service
import dagger.BindsInstance
import dagger.android.AndroidInjector
import io.androidalatan.resource.provider.ResourceProviderImpl
import io.androidalatan.resource.provider.api.ResourceProvider

@Suppress("DEPRECATION")
abstract class BaseServiceBuilder<T : Service> : AndroidInjector.Builder<T>() {

    override fun seedInstance(instance: T) {
        seedResourceProvider(ResourceProviderImpl(instance))
        seedService(instance)
        seedBaseService(instance)
    }

    @BindsInstance
    abstract fun seedService(instance: T)

    @BindsInstance
    abstract fun seedBaseService(instance: Service)

    @BindsInstance
    abstract fun seedResourceProvider(instance: ResourceProvider)

}