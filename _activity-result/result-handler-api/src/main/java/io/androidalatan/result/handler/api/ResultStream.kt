package io.androidalatan.result.handler.api

interface ResultStream {
    fun registerResultInfo(callback: Callback)
    fun unregisterResultInfo(callback: Callback)

    fun interface Callback {
        fun onResultInfo(resultInfo: ResultInfo)
    }
}