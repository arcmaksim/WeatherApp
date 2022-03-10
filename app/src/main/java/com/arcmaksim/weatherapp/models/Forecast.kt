package com.arcmaksim.weatherapp.models

import com.arcmaksim.weatherapp.R

class Forecast {

    lateinit var mCurrent: Current
    var mHourlyForecast = emptyArray<Hour>()
    var mDailyForecast = emptyArray<Day>()

    companion object {

        fun resolveIconId(
            icon: String
        ): Int = when (icon) {
            "clear-day" -> R.mipmap.clear_day
            "clear-night" -> R.mipmap.clear_night
            "rain" -> R.mipmap.rain
            "snow" -> R.mipmap.snow
            "sleet" -> R.mipmap.sleet
            "wind" -> R.mipmap.wind
            "fog" -> R.mipmap.fog
            "cloudy" -> R.mipmap.cloudy
            "partly_cloudy" -> R.mipmap.partly_cloudy
            "cloudy_night" -> R.mipmap.cloudy_night
            else -> R.mipmap.clear_day
        }

        fun convertFahrenheitToCelsius(
            temperatureInFahrenheit: Int
        ): Int = (temperatureInFahrenheit - 32) * 5 / 9

    }

}
