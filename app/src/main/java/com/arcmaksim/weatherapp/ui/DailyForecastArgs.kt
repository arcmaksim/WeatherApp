package com.arcmaksim.weatherapp.ui

import android.os.Parcelable
import com.arcmaksim.weatherapp.models.Day
import com.arcmaksim.weatherapp.models.Hour
import kotlinx.parcelize.Parcelize

@Parcelize
class DailyForecastArgs(
    val forecast: List<Day>,
) : Parcelable
