package com.arcmaksim.weatherapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForecastDto(
    val timezone: String,
    val currently: ForecastRecordDto,
    val hourly: ForecastRecordGroupDto<ForecastRecordDto>,
    val daily: ForecastRecordGroupDto<DailyForecastRecordDto>,
)
