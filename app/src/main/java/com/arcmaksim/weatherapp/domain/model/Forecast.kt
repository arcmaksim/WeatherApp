package com.arcmaksim.weatherapp.domain.model

class Forecast(
    val timezone: String,
    val current: ForecastRecord,
    val hourlyForecast: ForecastRecordGroup,
    val dailyForecast: ForecastRecordGroup,
)
