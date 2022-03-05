package io.androidalatan.bundle.collector.rx.adapter

import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.bundle.api.IntentData
import io.reactivex.rxjava3.core.Observable

fun BundleCollectorStream.intentData(): Observable<IntentData> {
    return Observable.create { emitter ->
        val callback = BundleCollectorStream.Callback {
            emitter.onNext(it)
        }
        registerIntentDataCallback(callback)

        emitter.setCancellable { unregisterIntentDataCallback(callback) }
    }
}