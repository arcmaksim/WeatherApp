package com.arcmaksim.weatherapp.models

class Day() {
    var mTime: Long = 0
    lateinit var mSummary: String
    var mTemperatureMax: Double = 0.0
    lateinit var mIconId: String
    lateinit var mTimezone: String

    fun getTemperature(): Int {
        return convertFahrenheitToCelcius(Math.round(mTemperatureMax).toInt())
    }

    fun convertFahrenheitToCelcius(temperatureInFahrenheit: Int) : Int {
        return ((temperatureInFahrenheit - 32) * 5 / 9)
    }
}