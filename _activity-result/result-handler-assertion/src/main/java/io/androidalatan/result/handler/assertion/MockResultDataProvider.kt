package io.androidalatan.result.handler.assertion

import android.content.Intent
import io.androidalatan.bundle.api.IntentData
import io.androidalatan.result.handler.api.ResultDataProvider

class MockResultDataProvider(
    private val mapIntentData: IntentData
) : ResultDataProvider {
    override fun hasValue(key: String) = mapIntentData.hasValue(key)

    override fun getStringOrNull(key: String): String? {
        return mapIntentData.getStringOrNull(key)
    }

    override fun getIntOrNull(key: String) = mapIntentData.getIntOrNull(key)

    override fun getByteOrNull(key: String): Byte? {
        return mapIntentData.getByteOrNull(key)
    }

    override fun getLongOrNull(key: String) = mapIntentData.getLongOrNull(key)

    override fun getDoubleOrNull(key: String) = mapIntentData.getDoubleOrNull(key)

    override fun getFloatOrNull(key: String) = mapIntentData.getFloatOrNull(key)

    override fun getBooleanOrNull(key: String) = mapIntentData.getBooleanOrNull(key)

    override fun getStringArray(key: String) = mapIntentData.getStringArray(key)
    override fun getUriStringOrNull(): String? = mapIntentData.getUriStringOrNull()
    override fun getTypeOrNull(): String? = mapIntentData.getTypeOrNull()

    override fun rawIntent(): Intent {
        return mapIntentData.rawIntent()
    }
}