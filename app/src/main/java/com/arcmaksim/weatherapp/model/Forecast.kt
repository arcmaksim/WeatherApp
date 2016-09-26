package com.arcmaksim.weatherapp.model

class Forecast {
    lateinit var mCurrent: Current
    var mHourlyForecast = emptyArray<Hour>()
    var mDailyForecast = emptyArray<Day>()
}