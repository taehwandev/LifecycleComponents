package io.androidalatan.view.event.legacy.rx

import io.androidalatan.view.event.api.ViewAccessor
import io.androidalatan.view.event.api.ViewAccessorStream
import io.reactivex.rxjava3.core.Observable

fun ViewAccessorStream.asObservable(): Observable<ViewAccessor> {
    return Observable.create { emitter ->
        val callback = ViewAccessorStream.Callback {
            emitter.onNext(it)
        }
        registerCallback(callback)

        emitter.setCancellable { unregisterCallback(callback) }
    }
}