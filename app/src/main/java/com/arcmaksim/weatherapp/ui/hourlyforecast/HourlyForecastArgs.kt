package com.arcmaksim.weatherapp.ui.hourlyforecast

import android.os.Parcelable
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import kotlinx.parcelize.Parcelize

@Parcelize
class HourlyForecastArgs(
    val timezone: String,
    val hourlyForecast: List<ForecastRecord>,
) : Parcelable
