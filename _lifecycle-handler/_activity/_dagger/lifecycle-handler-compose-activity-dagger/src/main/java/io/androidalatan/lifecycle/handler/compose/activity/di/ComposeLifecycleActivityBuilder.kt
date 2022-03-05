package io.androidalatan.lifecycle.handler.compose.activity.di

import dagger.BindsInstance
import io.androidalatan.compose.dialog.api.ComposeAlertDialogBuilderFactory
import io.androidalatan.dagger.builder.BaseActivityBuilder
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.compose.activity.ComposeLifecycleActivity

abstract class ComposeLifecycleActivityBuilder<T : ComposeLifecycleActivity> : BaseActivityBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleActivity(instance)
        seedComposeDialogFactory(instance.composeAlertDialogBuilderFactory)
    }

    @BindsInstance
    abstract fun seedComposeDialogFactory(alertDialogBuilderFactory: ComposeAlertDialogBuilderFactory)

    @BindsInstance
    abstract fun seedLifecycleActivity(instance: ComposeLifecycleActivity)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: LifecycleSource)

}