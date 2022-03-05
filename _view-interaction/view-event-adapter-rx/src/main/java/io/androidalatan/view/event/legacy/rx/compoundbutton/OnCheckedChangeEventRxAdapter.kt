package io.androidalatan.view.event.legacy.rx.compoundbutton

import io.androidalatan.view.event.api.compoundbutton.OnCheckedChangeEvent
import io.reactivex.rxjava3.core.Observable

fun OnCheckedChangeEvent.onCheckedChangeAsObservable(ignoreCurrentStatus: Boolean): Observable<Boolean> {

    return Observable.create<Boolean> { emitter ->
        val callback = OnCheckedChangeEvent.Callback {
            emitter.onNext(it)
        }
        registerOnCheckChangeCallback(callback)

        emitter.setCancellable { unregisterOnCheckChangeCallback(callback) }
    }
        .skip(if (ignoreCurrentStatus) 1 else 0)
        .distinctUntilChanged()

}