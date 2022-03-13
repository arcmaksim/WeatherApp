package com.arcmaksim.weatherapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arcmaksim.weatherapp.di.component.DaggerForecastComponent
import com.arcmaksim.weatherapp.presentation.currentforecast.CurrentForecastDestination
import com.arcmaksim.weatherapp.presentation.currentforecast.CurrentForecastScreen
import com.arcmaksim.weatherapp.presentation.currentforecast.ICurrentForecastViewModel
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastDestination
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastScreen
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastDestination
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastScreen

@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = CurrentForecastDestination.route,
    ) {
        composable(route = CurrentForecastDestination.route) {
            val viewModel: ICurrentForecastViewModel = daggerViewModel {
                DaggerForecastComponent.create().getCurrentForecastViewModel()
            }

            CurrentForecastScreen(
                viewModel = viewModel,
                showHourlyForecast = { timezone, records ->
                    navController.navigate(
                        DailyForecastDestination.getDestination(
                            timezone, records.toTypedArray(),
                        )
                    )
                },
                showDailyForecast = { timezone, records ->
                    navController.navigate(
                        HourlyForecastDestination.getDestination(
                            timezone, records.toTypedArray(),
                        )
                    )
                }
            )
        }

        composable(
            route = HourlyForecastDestination.route,
        ) {
            val args = DailyForecastDestination.parseArguments(it)
            DailyForecastScreen(
                args.timezone,
                args.records.toList(),
            )
        }

        composable(
            route = DailyForecastDestination.route,
        ) {
            val args = DailyForecastDestination.parseArguments(it)
            HourlyForecastScreen(
                args.timezone,
                args.records.toList(),
            )
        }
    }
}
