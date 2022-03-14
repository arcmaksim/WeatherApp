package com.arcmaksim.weatherapp.data.serializer

import com.squareup.moshi.Moshi

class ArgsDtoSerializer(
    private val moshi: Moshi,
) {

    fun <T> deserialize(
        clazz: Class<T>,
        value: String,
    ): T = moshi.adapter(clazz).fromJson(value) as T

    fun <T> serialize(
        clazz: Class<T>,
        value: T,
    ): String = moshi.adapter(clazz).toJson(value) as String

}
