package io.androidalatan.result.handler.assertion

import io.androidalatan.result.handler.api.ResultInfo
import io.androidalatan.result.handler.api.ResultStream

class MockResultStream : ResultStream {
    private val callbacks = mutableListOf<ResultStream.Callback>()

    fun putResultInfo(resultInfo: ResultInfo) {
        callbacks.forEach { it.onResultInfo(resultInfo) }
    }

    override fun registerResultInfo(callback: ResultStream.Callback) {
        callbacks.add(callback)
    }

    override fun unregisterResultInfo(callback: ResultStream.Callback) {
        callbacks.remove(callback)
    }

}