package com.arcmaksim.weatherapp.ui.currentforecast

import kotlinx.coroutines.flow.Flow

interface ICurrentForecastViewModel {

    val state: Flow<CurrentForecastScreenState>

    fun getForecast(
        isRefreshing: Boolean,
    )

}
