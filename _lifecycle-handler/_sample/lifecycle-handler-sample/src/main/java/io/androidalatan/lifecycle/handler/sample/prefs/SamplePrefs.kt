package io.androidalatan.lifecycle.handler.sample.prefs

import io.androidalatan.datastore.preference.annotations.getter.GetString
import io.androidalatan.datastore.preference.annotations.setter.Clear
import io.androidalatan.datastore.preference.annotations.setter.Set
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SamplePrefs {
    @Set(KEY_NAME)
    fun setName(name: String): Single<Boolean>

    @GetString(name = KEY_NAME, defaultValue = "")
    fun getName(): Observable<String>

    @Clear
    fun clear(): Completable

    companion object {
        const val KEY_NAME = "asdhjklk"
    }
}