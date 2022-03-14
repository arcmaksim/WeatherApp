package com.arcmaksim.weatherapp.domain

import com.arcmaksim.weatherapp.domain.model.Forecast
import javax.inject.Inject

class ForecastInteractor @Inject constructor(
    private val repo: IForecastRepository,
) : IForecastInteractor {

    private val apiKey = "0477e00681642c3961f2e5621998ce85"
    private val latitude = 59.941607
    private val longitude = 30.467906

    override suspend fun fetchForecast(): Result<Forecast> = runCatching {
        repo.fetchForecast(apiKey, latitude, longitude)
    }

}
