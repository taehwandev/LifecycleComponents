package io.androidalatan.result.handler

import android.content.Intent
import io.androidalatan.bundle.api.IntentData
import io.androidalatan.result.handler.api.ResultDataProvider

class ResultDataProviderImpl(
    private val resultData: IntentData
) : ResultDataProvider {
    override fun hasValue(key: String) = resultData.hasValue(key)

    override fun getStringOrNull(key: String): String? {
        return resultData.getStringOrNull(key)
    }

    override fun getIntOrNull(key: String) = resultData.getIntOrNull(key)

    override fun getByteOrNull(key: String): Byte? {
        return resultData.getByteOrNull(key)
    }

    override fun getLongOrNull(key: String) = resultData.getLongOrNull(key)

    override fun getDoubleOrNull(key: String) = resultData.getDoubleOrNull(key)

    override fun getFloatOrNull(key: String) = resultData.getFloatOrNull(key)

    override fun getBooleanOrNull(key: String) = resultData.getBooleanOrNull(key)

    override fun getStringArray(key: String) = resultData.getStringArray(key)

    override fun getUriStringOrNull(): String? = resultData.getUriStringOrNull()

    override fun getTypeOrNull(): String? = resultData.getTypeOrNull()

    override fun rawIntent(): Intent {
        return resultData.rawIntent()
    }

}