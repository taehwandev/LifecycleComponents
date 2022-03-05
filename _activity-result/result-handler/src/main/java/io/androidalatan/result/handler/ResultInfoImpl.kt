package io.androidalatan.result.handler

import io.androidalatan.result.handler.api.ResultDataProvider
import io.androidalatan.result.handler.api.ResultInfo

class ResultInfoImpl(
    private val requestCode: Int,
    private val resultCode: Int,
    private val resultDataProvider: ResultDataProvider
) : ResultInfo {
    override fun requestCode() = requestCode

    override fun resultCode() = resultCode

    override fun resultData() = resultDataProvider
}