package com.arcmaksim.weatherapp.domain

import com.arcmaksim.weatherapp.domain.model.Forecast

interface IForecastRepository {

    suspend fun fetchForecast(
        apiKey: String,
        latitude: Double,
        longitude: Double,
    ): Forecast

}
