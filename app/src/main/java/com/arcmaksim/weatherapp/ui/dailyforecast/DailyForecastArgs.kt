package com.arcmaksim.weatherapp.ui.dailyforecast

import android.os.Parcelable
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import kotlinx.parcelize.Parcelize

@Parcelize
class DailyForecastArgs(
    val timezone: String,
    val forecast: List<ForecastRecord>,
) : Parcelable
