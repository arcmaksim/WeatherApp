package com.arcmaksim.weatherapp.presentation.currentforecast

private const val destinationName = "currentForecast"

class CurrentForecastDestination {

    companion object {
        val route
            get() = destinationName
    }

}
