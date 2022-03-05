package io.androidalatan.view.event.legacy.rx.toolbar

import io.androidalatan.view.event.api.toolbar.OnToolbarMenuItemClick
import io.reactivex.rxjava3.core.Observable

fun OnToolbarMenuItemClick.onMenuItemClickAsObservable(): Observable<Int> {
    return Observable.create { emitter ->
        val callback = OnToolbarMenuItemClick.Callback { emitter.onNext(it) }
        registerOnMenuItemClickEvent(callback)

        emitter.setCancellable { unregisterOnMenuItemClickEvent(callback) }
    }
}