package io.androidalatan.backkey.rx.handler

import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.atomic.AtomicLong

fun BackKeyHandlerStream.onBackPressedAsObs(): Observable<Long> = Observable.create { emitter ->

    val count = AtomicLong(0)
    val callback = BackKeyHandlerStream.Callback {
        emitter.onNext(count.getAndIncrement())
    }
    registerCallback(callback)

    emitter.setCancellable {
        unregisterCallback(callback)
    }

}