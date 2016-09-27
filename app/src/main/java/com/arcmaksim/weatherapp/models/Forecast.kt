package com.arcmaksim.weatherapp.models

class Forecast {
    lateinit var mCurrent: Current
    var mHourlyForecast = emptyArray<Hour>()
    var mDailyForecast = emptyArray<Day>()
}