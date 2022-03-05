package io.androidalatan.lifecycle.handler.activity.di

import dagger.BindsInstance
import io.androidalatan.dagger.builder.BaseFragmentBuilder
import io.androidalatan.lifecycle.handler.activity.LifecycleBottomSheetDialogFragment
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource

abstract class LifecycleBottomSheetDialogFragmentBuilder<T : LifecycleBottomSheetDialogFragment> : BaseFragmentBuilder<T>() {

    override fun seedInstance(instance: T) {
        super.seedInstance(instance)
        seedLifecycleSource(instance)
        seedLifecycleBottomSheetDialogFragment(instance)
    }

    @BindsInstance
    abstract fun seedLifecycleBottomSheetDialogFragment(instance: LifecycleBottomSheetDialogFragment)

    @BindsInstance
    abstract fun seedLifecycleSource(instance: ChildLifecycleSource)

}