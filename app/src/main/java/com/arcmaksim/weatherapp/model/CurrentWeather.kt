package com.arcmaksim.weatherapp.model

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeather(var mTime: Long) {

    var mIcon: String? = null
    var mTemp: Double? = null
    var mHumidity: Double? = null
    var mPrecipChance: Double? = null
    var mSummary: String? = null
    var mTimezone: String? = null

    fun getFormattedTime(): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val timeString: String = formatter.format(mTime * 1000)
        return timeString
    }

}