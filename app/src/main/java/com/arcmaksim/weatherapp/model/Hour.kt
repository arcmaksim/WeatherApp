package com.arcmaksim.weatherapp.model

class Hour() {
    var mTime: Long = 0
    lateinit var mSummary: String
    var mTemperature: Double = 0.0
    lateinit var mIconId: String
    lateinit var mTimezone: String

    fun getTemperature(): Int {
        return convertFahrenheitToCelcius(Math.round(mTemperature).toInt())
    }

    fun convertFahrenheitToCelcius(temperatureInFahrenheit: Int) : Int {
        return ((temperatureInFahrenheit - 32) * 5 / 9)
    }
}