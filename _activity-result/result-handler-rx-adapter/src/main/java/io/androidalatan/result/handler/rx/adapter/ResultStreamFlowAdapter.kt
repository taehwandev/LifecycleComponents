package io.androidalatan.result.handler.rx.adapter

import io.androidalatan.result.handler.api.ResultInfo
import io.androidalatan.result.handler.api.ResultStream
import io.reactivex.rxjava3.core.Observable

fun ResultStream.anyResultInfoAsObservable(): Observable<ResultInfo> {
    return Observable.create { emitter ->
        val callback = ResultStream.Callback {
            emitter.onNext(it)
        }
        registerResultInfo(callback)
        emitter.setCancellable { unregisterResultInfo(callback) }
    }
}

fun ResultStream.resultInfoAsObservable(requestCode: Int): Observable<ResultInfo> {
    return anyResultInfoAsObservable().filter { it.requestCode() == requestCode }
}