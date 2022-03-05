package io.androidalatan.request.permission.flow.handler

import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionResult
import io.androidalatan.request.permission.api.PermissionStream
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.take

fun PermissionStream.requestAsFlow(
    permissions: Array<String>,
    reqCode: Int,
    explanation: ((PermissionExplanationBuilder) -> PermissionExplanation)? = null
): Flow<PermissionResult> {
    return callbackFlow {
        val callback = PermissionStream.Callback { handler ->
            handler.request(permissions, reqCode, explanation, {
                close(it)
            }, { trySend(it) })
        }
        registerAndRunCallback(callback)
        awaitClose {
            unregisterCallback(callback)
        }
    }.take(1)

}