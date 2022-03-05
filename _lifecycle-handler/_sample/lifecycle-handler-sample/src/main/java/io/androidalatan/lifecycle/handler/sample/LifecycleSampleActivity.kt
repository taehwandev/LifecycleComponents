package io.androidalatan.lifecycle.handler.sample

import android.content.Intent
import android.os.Bundle
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.datastore.preference.PreferencesBuilder
import io.androidalatan.lifecycle.handler.activity.LifecycleActivity
import io.androidalatan.lifecycle.handler.sample.adapter.PersonAdapter
import io.androidalatan.lifecycle.handler.sample.prefs.SamplePrefs
import io.androidalatan.rx.scheduler.api.SchedulerProvider
import io.androidalatan.view.event.api.ViewAccessor

class LifecycleSampleActivity : LifecycleActivity() {
    override val layoutId: Int
        get() = R.layout.sample_activity

    override fun viewInit(viewAccessor: ViewAccessor) {
        val schedulerProvider = SchedulerProvider()
        val dispatcherProvider = DispatcherProvider()
        val adapter = PersonAdapter()

        val samplePrefs = PreferencesBuilder(application)
            .name("sample-prefs")
            .create(SamplePrefs::class.java)

        val viewModel = SampleViewModel(
            this,
            this,
            schedulerProvider,
            dispatcherProvider,
            adapter,
            samplePrefs
        )

        viewAccessor.setVariable(BR.viewModel, viewModel)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSampleService()
    }

    private fun startSampleService() {
        startService(Intent(this, LifeCycleSampleService::class.java))
    }

    private fun stopSampleService() {
        stopService(Intent(this, LifeCycleSampleService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSampleService()
    }
}