package com.arcmaksim.weatherapp.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.TimeZone

@Parcelize
class Hour(
    val iconId: Int,
    val time: Long = 0,
    val temperature: Int = 0,
    val summary: String = "",
    val timezone: String = "",
) : Parcelable {

    val formattedTime: String
        get() {
            val formatter = SimpleDateFormat("H").apply {
                timeZone = TimeZone.getTimeZone(timezone)
            }
            return StringBuilder(formatter.format(time * 1000))
                .append(":00")
                .toString()
        }

}
