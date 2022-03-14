package com.arcmaksim.weatherapp.presentation.hourlyforecast

import com.arcmaksim.weatherapp.data.serializer.ArgsDtoSerializer
import com.arcmaksim.weatherapp.presentation.route.StatefulRoute

class HourlyForecastRoute(
    argsDtoSerializer: ArgsDtoSerializer,
) : StatefulRoute<HourlyForecastArgs>(
    argsDtoSerializer,
    "dailyForecast",
    HourlyForecastArgs::class.java,
)
