package com.arcmaksim.weatherapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForecastRecordGroupDto<T>(
    val summary: String,
    val icon: WeatherTypeDto,
    val data: List<T>,
)
