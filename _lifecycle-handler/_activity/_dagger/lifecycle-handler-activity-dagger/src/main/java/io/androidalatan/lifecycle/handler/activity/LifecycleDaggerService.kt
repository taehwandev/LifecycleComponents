package io.androidalatan.lifecycle.handler.activity

import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class LifecycleDaggerService : LifecycleService(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected fun unneededInject() {
        // https://github.com/google/dagger/issues/955#issuecomment-347749874
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun androidInjector() = androidInjector

}