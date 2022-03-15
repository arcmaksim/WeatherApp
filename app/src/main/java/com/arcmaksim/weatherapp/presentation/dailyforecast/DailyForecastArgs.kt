package com.arcmaksim.weatherapp.presentation.dailyforecast

import com.arcmaksim.weatherapp.presentation.model.ForecastRecordArgsDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DailyForecastArgs(
    val timezone: String,
    val records: List<ForecastRecordArgsDto>,
)
