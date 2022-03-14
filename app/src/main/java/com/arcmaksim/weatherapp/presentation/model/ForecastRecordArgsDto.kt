package com.arcmaksim.weatherapp.presentation.model

import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.domain.model.WeatherType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForecastRecordArgsDto(
    val time: Long,
    val summary: String,
    val type: WeatherType,
    val temperature: Int,
    val precipitationProbability: Int,
    val apparentTemperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val windGust: Int,
)

fun ForecastRecordArgsDto.toEntity(): ForecastRecord = ForecastRecord(
    time = time,
    summary = summary,
    type = type,
    temperature = temperature,
    precipitationProbability = precipitationProbability,
    apparentTemperature = apparentTemperature,
    humidity = humidity,
    windSpeed = windSpeed,
    windGust = windGust,
)

fun ForecastRecord.toArgsDto(): ForecastRecordArgsDto = ForecastRecordArgsDto(
    time = time,
    summary = summary,
    type = type,
    temperature = temperature,
    precipitationProbability = precipitationProbability,
    apparentTemperature = apparentTemperature,
    humidity = humidity,
    windSpeed = windSpeed,
    windGust = windGust,
)
