package io.androidalatan.result.handler

import android.content.Intent
import io.androidalatan.bundle.IntentDataImpl
import io.androidalatan.bundle.api.IntentData
import io.androidalatan.result.handler.api.ResultStream

class ResultStreamImpl : ResultStream {

    private val callbacks = mutableListOf<ResultStream.Callback>()
    private val lock = Any()

    override fun registerResultInfo(callback: ResultStream.Callback) {
        synchronized(lock) {
            callbacks.add(callback)
        }
    }

    override fun unregisterResultInfo(callback: ResultStream.Callback) {
        synchronized(lock) {
            callbacks.remove(callback)
        }
    }

    fun newResultInfo(requestCode: Int, resultCode: Int, intentData: Intent?) {
        synchronized(lock) {
            val resultInfo = ResultInfoImpl(
                requestCode = requestCode,
                resultCode = resultCode,
                resultDataProvider = ResultDataProviderImpl(intentData?.let { IntentDataImpl(it) } ?: IntentData.EMPTY))
            callbacks.forEach { it.onResultInfo(resultInfo) }
        }
    }

}