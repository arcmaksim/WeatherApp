package com.arcmaksim.weatherapp.models

import com.arcmaksim.weatherapp.R
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
        val formatter: SimpleDateFormat = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val timeString: String = formatter.format(mTime * 1000)
        return timeString
    }

    fun getIconId(): Int {
        val iconID: Int
        when(mIcon) {
            "clear-day" -> iconID = R.mipmap.clear_day
            "clear-night" -> iconID = R.mipmap.clear_night
            "rain" -> iconID = R.mipmap.rain
            "snow" -> iconID = R.mipmap.snow
            "sleet" -> iconID = R.mipmap.sleet
            "wind" -> iconID = R.mipmap.wind
            "fog" -> iconID = R.mipmap.fog
            "cloudy" -> iconID = R.mipmap.cloudy
            "partly_cloudy" -> iconID = R.mipmap.partly_cloudy
            "cloudy_night" -> iconID = R.mipmap.cloudy_night
            else -> iconID = R.mipmap.clear_day
        }
        return iconID
    }

    fun getTemperature(): Int {
        return convertFahrenheitToCelcius(Math.round(mTemperature).toInt())
    }

    fun convertFahrenheitToCelcius(temperatureInFahrenheit: Int) : Int {
        return ((temperatureInFahrenheit - 32) * 5 / 9)
    }

    fun getPrecipChance(): Int {
        return Math.round(mPrecipChance * 100).toInt()
    }

    fun getHumidity(): Int {
        return Math.round(mHumidity* 100).toInt()
    }

}