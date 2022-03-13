package com.arcmaksim.weatherapp.presentation.currentforecast

import com.arcmaksim.weatherapp.domain.model.Forecast

data class CurrentForecastState(
    val forecast: Forecast? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
