package io.androidalatan.request.permission.rx.handler

import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionResult
import io.androidalatan.request.permission.api.PermissionStream
import io.reactivex.rxjava3.core.Single

fun PermissionStream.requestAsObs(
    permissions: Array<String>,
    reqCode: Int,
    explanation: ((PermissionExplanationBuilder) -> PermissionExplanation)? = null
): Single<PermissionResult> {
    return Single.create { emitter ->
        val callback = PermissionStream.Callback { handler ->
            handler.request(
                permissions,
                reqCode,
                explanation,
                { emitter.tryOnError(it) },
                { emitter.onSuccess(it) }
            )
        }
        this.registerAndRunCallback(callback)
        emitter.setCancellable {
            unregisterCallback(callback)
        }
    }

}