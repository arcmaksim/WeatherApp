package com.arcmaksim.weatherapp.presentation.dailyforecast

import com.arcmaksim.weatherapp.data.serializer.ArgsDtoSerializer
import com.arcmaksim.weatherapp.presentation.route.StatefulRoute

class DailyForecastRoute(
    argsDtoSerializer: ArgsDtoSerializer,
) : StatefulRoute<DailyForecastArgs>(
    argsDtoSerializer,
    "dailyForecast",
    DailyForecastArgs::class.java,
)
