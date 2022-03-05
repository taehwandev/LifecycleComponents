package io.androidalatan.lifecycle.handler.sample.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

data class Person(val name: String)

object PersonMoshiAdapter {

    @ToJson
    fun toJson(jsonWriter: JsonWriter, value: Person?) {
        jsonWriter.beginObject()
        if (value != null) {
            jsonWriter.name("name")
            jsonWriter.value(value.name)
        }

        jsonWriter.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): Person {
        reader.beginObject()
        var name: String? = null
        if (reader.selectName(JsonReader.Options.of("name")) == 0) {
            name = reader.readJsonValue() as? String
        }

        val testPersonClass =
            Person(name ?: throw NullPointerException("name is null"))

        reader.endObject()
        return testPersonClass
    }
}