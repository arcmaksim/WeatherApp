package com.arcmaksim.weatherapp.ui

import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.domain.model.WeatherType

fun WeatherType.toIconResId(): Int = when (this) {
    WeatherType.ClearDay -> R.mipmap.clear_day
    WeatherType.ClearNight -> R.mipmap.clear_night
    WeatherType.Rain -> R.mipmap.rain
    WeatherType.Snow -> R.mipmap.snow
    WeatherType.Sleet -> R.mipmap.sleet
    WeatherType.Wind -> R.mipmap.wind
    WeatherType.Fog -> R.mipmap.fog
    WeatherType.Cloudy -> R.mipmap.cloudy
    WeatherType.PartlyCloudy -> R.mipmap.partly_cloudy
    WeatherType.CloudyNight -> R.mipmap.cloudy_night
}
