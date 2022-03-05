package io.androidalatan.lifecycle.handler.compose.activity.di

import dagger.BindsInstance
import io.androidalatan.dagger.builder.BaseFragmentBuilder
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.compose.activity.ComposeLifecycleBottomSheetDialogFragment

abstract class ComposeLifecycleBottomSheetDialogFragmentBuilder<T : ComposeLifecycleBottomSheetDialogFragment> : BaseFragmentBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleBottomSheetDialogFragment(instance)
    }

    @BindsInstance
    abstract fun seedLifecycleBottomSheetDialogFragment(instance: ComposeLifecycleBottomSheetDialogFragment)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: ChildLifecycleSource)

}