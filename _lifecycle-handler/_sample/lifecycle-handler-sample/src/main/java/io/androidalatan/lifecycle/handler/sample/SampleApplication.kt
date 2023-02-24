package io.androidalatan.lifecycle.handler.sample

import android.app.Application
import android.util.Log
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.datastore.preference.PreferencesBuilderInitializer
import io.androidalatan.datastore.preference.adapter.api.ClearAdapter
import io.androidalatan.datastore.preference.adapter.api.GetAdapter
import io.androidalatan.datastore.preference.adapter.api.SetAdapter
import io.androidalatan.datastore.preference.adapter.flow.FlowClearAdapter
import io.androidalatan.datastore.preference.adapter.flow.FlowGetAdapter
import io.androidalatan.datastore.preference.adapter.flow.FlowSetAdapter
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer
import io.androidalatan.lifecycle.handler.invokeradapter.flow.FlowInvokeAdapter

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        InvokeAdapterInitializer.initialize(
            factories = listOf(FlowInvokeAdapter.Factory()),
            errorLogger = { Log.e("InvokeManager", "InvokeManager got error", it) }
        )

        val dispatcherProvider = DispatcherProvider()

        PreferencesBuilderInitializer.init(
            setAdapterFactories = listOf(SetAdapter.Factory {
                FlowSetAdapter(dispatcherProvider.io())
            }),
            getAdapterFactories = listOf(GetAdapter.Factory {
                FlowGetAdapter(it, dispatcherProvider.io())
            }),
            clearAdapterFactories = listOf(ClearAdapter.Factory {
                FlowClearAdapter(dispatcherProvider.io())
            })
        )

    }
}