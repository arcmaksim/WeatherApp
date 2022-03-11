package com.arcmaksim.weatherapp.domain.model

class ForecastRecordGroup(
    val summary: String,
    val type: WeatherType,
    val records: List<ForecastRecord>,
)
