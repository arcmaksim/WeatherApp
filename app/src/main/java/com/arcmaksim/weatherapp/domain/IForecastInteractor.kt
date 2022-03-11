package com.arcmaksim.weatherapp.domain

import com.arcmaksim.weatherapp.domain.model.Forecast

interface IForecastInteractor {

    suspend fun fetchForecast(): Result<Forecast>

}
