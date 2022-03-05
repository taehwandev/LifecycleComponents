package io.androidalatan.dagger.builder

import android.app.Activity
import dagger.BindsInstance
import dagger.android.AndroidInjector
import io.androidalatan.dialog.AlertDialogBuilderFactoryImpl
import io.androidalatan.dialog.api.AlertDialogBuilderFactory
import io.androidalatan.resource.provider.ResourceProviderImpl
import io.androidalatan.resource.provider.api.ResourceProvider

@Suppress("DEPRECATION")
abstract class BaseActivityBuilder<T : Activity> : AndroidInjector.Builder<T>() {

    override fun seedInstance(instance: T) {
        seedResourceProvider(ResourceProviderImpl(instance))
        seedActivity(instance)
        seedBaseActivity(instance)
        seedDialogFactory(AlertDialogBuilderFactoryImpl(lazy { instance }))
    }

    @BindsInstance
    abstract fun seedDialogFactory(alertDialogBuilderFactory: AlertDialogBuilderFactory)

    @BindsInstance
    abstract fun seedActivity(instance: T)

    @BindsInstance
    abstract fun seedBaseActivity(instance: Activity)

    @BindsInstance
    abstract fun seedResourceProvider(instance: ResourceProvider)

}