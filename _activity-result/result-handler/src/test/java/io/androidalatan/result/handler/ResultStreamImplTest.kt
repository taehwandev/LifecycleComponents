package io.androidalatan.result.handler

import io.androidalatan.result.handler.api.ResultInfo
import io.androidalatan.result.handler.api.ResultStream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResultStreamImplTest {

    private val resultStream = ResultStreamImpl()

    @Test
    fun newResultInfo() {
        var lastResultInfo: ResultInfo? = null
        val callback = ResultStream.Callback {
            lastResultInfo = it
        }
        resultStream.registerResultInfo(callback)
        resultStream.newResultInfo(1, 2, null)

        Assertions.assertEquals(1, lastResultInfo?.requestCode())
        Assertions.assertEquals(2, lastResultInfo?.resultCode())

        resultStream.unregisterResultInfo(callback)
    }
}