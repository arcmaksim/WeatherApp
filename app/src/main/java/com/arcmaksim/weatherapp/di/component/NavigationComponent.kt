package com.arcmaksim.weatherapp.di.component

import com.arcmaksim.weatherapp.di.module.NavigationModule
import com.arcmaksim.weatherapp.presentation.currentforecast.CurrentForecastRoute
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastRoute
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastRoute
import dagger.Component

@Component(modules = [NavigationModule::class])
interface NavigationComponent {

    fun getCurrentForecastRoute(): CurrentForecastRoute

    fun getDailyForecastRoute(): DailyForecastRoute

    fun getHourlyForecastRoute(): HourlyForecastRoute

}
