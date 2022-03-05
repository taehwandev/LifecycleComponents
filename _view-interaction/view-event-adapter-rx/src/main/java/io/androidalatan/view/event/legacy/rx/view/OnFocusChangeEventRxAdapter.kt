package io.androidalatan.view.event.legacy.rx.view

import io.androidalatan.view.event.api.view.OnFocusChangeEvent
import io.reactivex.rxjava3.core.Observable

fun OnFocusChangeEvent.onFocusChangeAsObservable(): Observable<Boolean> {
    return Observable.create<Boolean> { emitter ->
        val callback = OnFocusChangeEvent.Callback { isFocused ->
            emitter.onNext(isFocused)
        }
        registerOnFocusChangeEvent(callback)

        emitter.setCancellable {
            unregisterOnFocusChangeEvent(callback)
        }
    }
        .distinctUntilChanged()
}