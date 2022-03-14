package com.arcmaksim.weatherapp.di.module

import com.arcmaksim.weatherapp.data.serializer.ArgsDtoSerializer
import com.arcmaksim.weatherapp.presentation.currentforecast.CurrentForecastRoute
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastRoute
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastRoute
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class NavigationModule {

    @Provides
    @Reusable
    fun provideArgsSerializer(): ArgsDtoSerializer = ArgsDtoSerializer(
        Moshi.Builder().build(),
    )

    @Provides
    fun provideCurrentForecastRoute(): CurrentForecastRoute = CurrentForecastRoute()

    @Provides
    fun provideDailyForecastRoute(
        argsDtoSerializer: ArgsDtoSerializer,
    ): DailyForecastRoute = DailyForecastRoute(argsDtoSerializer)

    @Provides
    fun provideHourlyForecastRoute(
        argsDtoSerializer: ArgsDtoSerializer,
    ): HourlyForecastRoute = HourlyForecastRoute(argsDtoSerializer)

}
