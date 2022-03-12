package com.arcmaksim.weatherapp.presentation.currentforecast

import kotlinx.coroutines.flow.Flow

interface ICurrentForecastViewModel {

    val state: Flow<CurrentForecastFragmentState>

    fun getForecast(
        isRefreshing: Boolean,
    )

}
