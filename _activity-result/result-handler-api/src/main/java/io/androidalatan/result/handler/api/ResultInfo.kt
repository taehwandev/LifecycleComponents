package io.androidalatan.result.handler.api

interface ResultInfo {
    fun requestCode(): Int
    fun resultCode(): Int
    fun resultData(): ResultDataProvider
}