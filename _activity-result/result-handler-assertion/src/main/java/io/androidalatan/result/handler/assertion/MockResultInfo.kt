package io.androidalatan.result.handler.assertion

import io.androidalatan.bundle.api.IntentData
import io.androidalatan.result.handler.api.ResultDataProvider
import io.androidalatan.result.handler.api.ResultInfo

data class MockResultInfo(
    private val requestCode: Int = 0,
    private val resultCode: Int = 0,
    private val mapData: IntentData
) : ResultInfo {
    override fun requestCode(): Int {
        return requestCode
    }

    override fun resultCode(): Int {
        return resultCode
    }

    override fun resultData(): ResultDataProvider {
        return MockResultDataProvider(mapData)
    }
}