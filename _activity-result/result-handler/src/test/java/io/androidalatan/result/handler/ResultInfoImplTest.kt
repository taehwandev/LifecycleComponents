package io.androidalatan.result.handler

import io.androidalatan.bundle.assertion.MapIntentData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResultInfoImplTest {

    private val reqCode = 1021
    private val resCode = 2
    private val dataProvider = ResultDataProviderImpl(MapIntentData(mutableMapOf()))
    private val resultInfo = ResultInfoImpl(reqCode, resCode, dataProvider)

    @Test
    fun requestCode() {
        Assertions.assertEquals(reqCode, resultInfo.requestCode())
    }

    @Test
    fun resultCode() {
        Assertions.assertEquals(resCode, resultInfo.resultCode())
    }

    @Test
    fun resultData() {
        Assertions.assertEquals(dataProvider, resultInfo.resultData())
    }
}