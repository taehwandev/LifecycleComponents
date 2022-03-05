package io.androidalatan.view.event.legacy.rx.view

import io.androidalatan.view.event.api.view.OnLongClickEvent
import io.reactivex.rxjava3.core.Observable

fun OnLongClickEvent.onLongClickAsObservable(): Observable<Long> {
    return Observable.create { emitter ->
        var clicked = 0L
        val callback = OnLongClickEvent.Callback {
            emitter.onNext(clicked++)
        }
        registerOnLongClickEvent(callback)

        emitter.setCancellable {
            unregisterOnLongClickEvent(callback)
        }
    }
}