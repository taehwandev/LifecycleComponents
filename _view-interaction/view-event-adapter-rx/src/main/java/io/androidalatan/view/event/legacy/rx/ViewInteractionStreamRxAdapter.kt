package io.androidalatan.view.event.legacy.rx

import io.androidalatan.view.event.api.ViewInteractionController
import io.androidalatan.view.event.api.ViewInteractionStream
import io.reactivex.rxjava3.core.Observable

fun ViewInteractionStream.asObservable(): Observable<ViewInteractionController> {
    return Observable.create { emitter ->
        val callback = ViewInteractionStream.Callback {
            emitter.onNext(it)
        }
        registerCallback(callback)

        emitter.setCancellable { unregisterCallback(callback) }
    }
}