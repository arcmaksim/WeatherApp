package com.arcmaksim.weatherapp.models

import java.text.SimpleDateFormat
import java.util.*

class Current() {

    var mTime: Long = 0
    lateinit var mIcon: String
    var mTemperature: Double = 0.0
    var mHumidity: Double = 0.0
    var mPrecipChance: Double = 0.0
    lateinit var mSummary: String
    lateinit var mTimezone: String

    fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val timeString = formatter.format(mTime * 1000)
        return timeString
    }

    fun getIconId(): Int {
        return Forecast.resolveIconId(mIcon)
    }

    fun getTemperature(): Int {
        return Forecast.convertFahrenheitToCelsius(Math.round(mTemperature).toInt())
    }

    fun getPrecipChance(): Int {
        return Math.round(mPrecipChance * 100).toInt()
    }

    fun getHumidity(): Int {
        return Math.round(mHumidity* 100).toInt()
    }

}
