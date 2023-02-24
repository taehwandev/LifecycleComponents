package io.androidalatan.lifecycle.handler.sample.prefs

import io.androidalatan.datastore.preference.annotations.getter.GetString
import io.androidalatan.datastore.preference.annotations.setter.Clear
import io.androidalatan.datastore.preference.annotations.setter.Set
import kotlinx.coroutines.flow.Flow

interface SamplePrefs {
    @Set(KEY_NAME)
    fun setName(name: String): Flow<Boolean>

    @GetString(name = KEY_NAME, defaultValue = "")
    fun getName(): Flow<String>

    @Clear
    fun clear(): Flow<Boolean>

    companion object {
        const val KEY_NAME = "asdhjklk"
    }
}