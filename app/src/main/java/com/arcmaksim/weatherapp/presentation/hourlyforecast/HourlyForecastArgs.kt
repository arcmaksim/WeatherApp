package com.arcmaksim.weatherapp.presentation.hourlyforecast

import com.arcmaksim.weatherapp.presentation.model.ForecastRecordArgsDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HourlyForecastArgs(
    val timezone: String,
    val records: List<ForecastRecordArgsDto>,
)
