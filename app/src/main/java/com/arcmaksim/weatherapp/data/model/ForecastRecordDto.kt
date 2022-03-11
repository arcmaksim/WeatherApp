package com.arcmaksim.weatherapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForecastRecordDto(
    val time: Long,
    val summary: String,
    val icon: WeatherTypeDto,
    val temperature: Double,
    val precipIntensity: Double,
    val precipProbability: Double,
    val apparentTemperature: Double,
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
