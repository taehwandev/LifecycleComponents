package io.androidalatan.result.handler.flow.adapter

import io.androidalatan.result.handler.api.ResultInfo
import io.androidalatan.result.handler.api.ResultStream
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filter

fun ResultStream.anyResultInfoAsFlow(): Flow<ResultInfo> {
    return callbackFlow {
        val callback = ResultStream.Callback {
            trySend(it)
        }
        registerResultInfo(callback)
        awaitClose { unregisterResultInfo(callback) }
    }
}

fun ResultStream.resultInfoAsFlow(requestCode: Int): Flow<ResultInfo> {
    return anyResultInfoAsFlow().filter { it.requestCode() == requestCode }
}