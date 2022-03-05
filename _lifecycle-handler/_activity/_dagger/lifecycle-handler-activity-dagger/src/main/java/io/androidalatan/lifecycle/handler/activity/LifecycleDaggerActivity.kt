package io.androidalatan.lifecycle.handler.activity

import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class LifecycleDaggerActivity : LifecycleActivity(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected fun unneededInject() {
        // https://github.com/google/dagger/issues/955#issuecomment-347749874
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector() = androidInjector

}