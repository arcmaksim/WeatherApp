package com.arcmaksim.weatherapp.ui.currentforecast

import com.arcmaksim.weatherapp.domain.model.Forecast

data class CurrentForecastScreenState(
    val forecast: Forecast? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
