package io.androidalatan.view.event.legacy.rx.toolbar

import io.androidalatan.view.event.api.toolbar.OnToolbarNavigationClick
import io.reactivex.rxjava3.core.Observable

fun OnToolbarNavigationClick.onNavigationClickAsObservable(): Observable<Long> {
    return Observable.create { emitter ->
        var clickCount = 0L
        val callback = OnToolbarNavigationClick.Callback { emitter.onNext(clickCount++) }
        registerOnNavigationClickEvent(callback)

        emitter.setCancellable { unregisterOnNavigationClickEvent(callback) }
    }
}