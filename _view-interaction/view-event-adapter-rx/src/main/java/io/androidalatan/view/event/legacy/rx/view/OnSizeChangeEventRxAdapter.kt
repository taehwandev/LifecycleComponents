package io.androidalatan.view.event.legacy.rx.view

import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.reactivex.rxjava3.core.Observable

fun OnSizeChangeEvent.onSizeChangeAsObservable(): Observable<OnSizeChangeEvent.ViewSize> {
    return Observable.create<OnSizeChangeEvent.ViewSize> { emitter ->
        val callback = OnSizeChangeEvent.Callback { viewSize ->
            emitter.onNext(viewSize)
        }
        registerOnSizeChangeCallback(callback)

        emitter.setCancellable {
            unregisterOnSizeChangeCallback(callback)
        }
    }
        .distinctUntilChanged()
}