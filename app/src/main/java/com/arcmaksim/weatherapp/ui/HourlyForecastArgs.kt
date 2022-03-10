package com.arcmaksim.weatherapp.ui

import android.os.Parcelable
import com.arcmaksim.weatherapp.models.Hour
import kotlinx.parcelize.Parcelize

@Parcelize
class HourlyForecastArgs(
    val hourlyForecast: List<Hour>,
) : Parcelable
