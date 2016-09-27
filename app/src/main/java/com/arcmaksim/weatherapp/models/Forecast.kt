package com.arcmaksim.weatherapp.models

import com.arcmaksim.weatherapp.R

class Forecast {
    lateinit var mCurrent: Current
    var mHourlyForecast = emptyArray<Hour>()
    var mDailyForecast = emptyArray<Day>()

    companion object {
        fun getIconId(icon: String): Int {
            val iconID: Int
            when(icon) {
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

        fun convertFahrenheitToCelcius(temperatureInFahrenheit: Int) : Int {
            return ((temperatureInFahrenheit - 32) * 5 / 9)
        }
    }


}