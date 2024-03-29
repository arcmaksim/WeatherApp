package com.arcmaksim.weatherapp.di.module

import com.arcmaksim.weatherapp.data.repository.ForecastRepository
import com.arcmaksim.weatherapp.domain.ForecastInteractor
import com.arcmaksim.weatherapp.domain.IForecastInteractor
import com.arcmaksim.weatherapp.domain.IForecastRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ForecastModule {

    @Binds
    abstract fun bindForecastInteractor(
        interactor: ForecastInteractor,
    ) : IForecastInteractor

    @Binds
    abstract fun bindForecastRepository(
        repository: ForecastRepository,
    ) : IForecastRepository

}
