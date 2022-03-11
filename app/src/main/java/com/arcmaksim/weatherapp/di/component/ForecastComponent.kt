package com.arcmaksim.weatherapp.di.component

import com.arcmaksim.weatherapp.di.module.ForecastModule
import com.arcmaksim.weatherapp.di.module.NetworkModule
import com.arcmaksim.weatherapp.ui.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class, ForecastModule::class])
interface ForecastComponent {

    fun inject(
        mainActivity: MainActivity,
    )

}
