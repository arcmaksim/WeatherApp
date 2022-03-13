package com.arcmaksim.weatherapp.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ParcelableArrayNavType<T : Parcelable>(
    isNullableAllowed: Boolean = false,
) : NavType<Array<T>>(isNullableAllowed) {

    override fun get(
        bundle: Bundle,
        key: String,
    ): Array<T> = bundle.getParcelableArray(key) as Array<T>

    override fun parseValue(
        value: String,
    ): Array<T> = Gson().fromJson(value, object : TypeToken<Array<T>>() {}.type)

    override fun put(
        bundle: Bundle,
        key: String,
        value: Array<T>,
    ) {
        bundle.putSerializable(key, value)
    }

}
