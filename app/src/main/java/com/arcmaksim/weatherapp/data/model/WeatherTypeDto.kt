package com.arcmaksim.weatherapp.data.model

import com.squareup.moshi.Json

enum class WeatherTypeDto {
    @Json(name = "clear-day") ClearDay,
    @Json(name = "clear-night") ClearNight,
    @Json(name = "rain") Rain,
    @Json(name = "snow") Snow,
    @Json(name = "sleet") Sleet,
    @Json(name = "wind") Wind,
    @Json(name = "fog") Fog,
    @Json(name = "cloudy") Cloudy,
    @Json(name = "partly_cloudy") PartlyCloudy,
    @Json(name = "cloudy_night") CloudyNight,
}
