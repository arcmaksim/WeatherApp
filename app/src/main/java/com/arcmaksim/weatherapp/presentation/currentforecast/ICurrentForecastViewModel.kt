package com.arcmaksim.weatherapp.presentation.currentforecast

import kotlinx.coroutines.flow.Flow

interface ICurrentForecastViewModel {

    val state: Flow<CurrentForecastState>

    fun getForecast(
        isRefreshing: Boolean,
    )

    fun dismissError()

}
