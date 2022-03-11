package com.arcmaksim.weatherapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DailyForecastRecordDto(
    val time: Long,
    val summary: String,
    val icon: WeatherTypeDto,
    val temperatureMax: Double,
    val precipIntensity: Double,
    val precipProbability: Double,
    val apparentTemperatureMax: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Double,
    val cloudCover: Double,
    val uvIndex: Double,
    val visibility: Double,
    val ozone: Double,
)
