package com.arcmaksim.weatherapp

import android.app.Application
import com.arcmaksim.weatherapp.di.DaggerForecastComponent
import com.arcmaksim.weatherapp.di.component.ForecastComponent

class App : Application() {

    val forecastComponent: ForecastComponent by lazy { DaggerForecastComponent.create() }

}
