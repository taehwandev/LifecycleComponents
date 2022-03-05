package io.androidalatan.lifecycle.handler.sample

import io.androidalatan.lifecycle.handler.activity.LifecycleService

class LifeCycleSampleService : LifecycleService() {

    override fun serviceInit() {
        SampleServiceViewModel(this.application, this)
    }

}