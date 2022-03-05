package io.androidalatan.view.event.legacy.rx.view

import io.androidalatan.view.event.api.view.OnClickEvent
import io.reactivex.rxjava3.core.Observable

fun OnClickEvent.onClickAsObservable(): Observable<Long> {
    return Observable.create { emitter ->
        var clicked = 0L
        val callback = OnClickEvent.Callback {
            emitter.onNext(clicked++)
        }
        registerOnClickEvent(callback)

        emitter.setCancellable {
            unregisterOnClickEvent(callback)
        }
    }
}