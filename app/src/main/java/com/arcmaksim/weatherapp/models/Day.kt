package com.arcmaksim.weatherapp.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.TimeZone

@Parcelize
class Day(
    val iconId: Int,
    val time: Long = 0,
    val summary: String = "",
    val temperature: Int = 0,
    val timezone: String = "",
) : Parcelable {

    val dayOfTheWeek: String
        get() = SimpleDateFormat("EEEE").apply {
            timeZone = TimeZone.getTimeZone(timezone)
        }.format(time * 1000)

}
