package io.androidalatan.lifecycle.handler.sample

import android.app.Application
import android.widget.Toast
import io.androidalatan.coroutine.handler.annotations.RunDispatcherUI
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.annotations.sync.OnDestroy
import io.androidalatan.lifecycle.handler.annotations.sync.OnPause
import io.androidalatan.lifecycle.handler.annotations.sync.OnResumed
import io.androidalatan.lifecycle.handler.annotations.sync.OnStarted
import io.androidalatan.lifecycle.handler.annotations.sync.OnStop
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import kotlinx.coroutines.delay

class SampleServiceViewModel(private val application: Application, lifecycleSource: LifecycleSource) : LifecycleListener(lifecycleSource) {

    @OnCreated
    @RunDispatcherUI
    suspend fun testSuspendCreate() {
        delay(500)
        showToast("Suspend delay Service Create")
    }

    @OnCreated
    fun testCreate() {
        showToast("Service Create")
    }

    @OnStarted
    fun testStart() {
        showToast("Service Start")
    }

    @OnResumed
    fun testResume() {
        showToast("Service Resume")
    }

    @OnPause
    @RunDispatcherUI
    suspend fun testSuspendPause() {
        showToast("Suspend Service pause")
    }

    @OnPause
    fun testPause() {
        showToast("Service Pause")
    }

    @OnStop
    fun testStop() {
        showToast("Service Stop")
    }

    @OnDestroy
    fun testDestroy() {
        showToast("Service Destroy")
    }

    private fun showToast(text: String) {
        Toast.makeText(
            application.applicationContext,
            text,
            Toast.LENGTH_SHORT
        )
            .show()
    }
}