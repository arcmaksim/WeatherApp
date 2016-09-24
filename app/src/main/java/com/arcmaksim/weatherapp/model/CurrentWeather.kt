package com.arcmaksim.weatherapp.model

import android.util.Log
import com.arcmaksim.weatherapp.R
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

}