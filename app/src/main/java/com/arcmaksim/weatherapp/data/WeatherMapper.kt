package com.arcmaksim.weatherapp.data

import com.arcmaksim.weatherapp.data.model.WeatherTypeDto
import com.arcmaksim.weatherapp.domain.model.WeatherType

fun WeatherTypeDto.toEntity() = when (this) {
    WeatherTypeDto.ClearDay -> WeatherType.ClearDay
    WeatherTypeDto.ClearNight -> WeatherType.ClearNight
    WeatherTypeDto.Cloudy -> WeatherType.Cloudy
    WeatherTypeDto.CloudyNight -> WeatherType.CloudyNight
    WeatherTypeDto.Fog -> WeatherType.Fog
    WeatherTypeDto.PartlyCloudy -> WeatherType.PartlyCloudy
    WeatherTypeDto.Rain -> WeatherType.Rain
    WeatherTypeDto.Sleet -> WeatherType.Sleet
    WeatherTypeDto.Snow -> WeatherType.Snow
    WeatherTypeDto.Wind -> WeatherType.Wind
}
