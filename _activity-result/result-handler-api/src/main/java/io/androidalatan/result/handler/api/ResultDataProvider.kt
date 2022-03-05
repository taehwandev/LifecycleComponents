package io.androidalatan.result.handler.api

import android.content.Intent

interface ResultDataProvider {

    fun hasValue(key: String): Boolean
    fun getStringOrNull(key: String): String?
    fun getIntOrNull(key: String): Int?
    fun getByteOrNull(key: String): Byte?
    fun getLongOrNull(key: String): Long?
    fun getDoubleOrNull(key: String): Double?
    fun getFloatOrNull(key: String): Float?
    fun getBooleanOrNull(key: String): Boolean?
    fun getStringArray(key: String): Array<String>
    fun getUriStringOrNull(): String?
    fun rawIntent(): Intent
    fun getTypeOrNull(): String?
}