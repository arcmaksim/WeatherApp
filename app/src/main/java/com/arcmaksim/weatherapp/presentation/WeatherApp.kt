package com.arcmaksim.weatherapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arcmaksim.weatherapp.di.component.DaggerForecastComponent
import com.arcmaksim.weatherapp.di.component.DaggerNavigationComponent
import com.arcmaksim.weatherapp.presentation.currentforecast.CurrentForecastScreen
import com.arcmaksim.weatherapp.presentation.currentforecast.ICurrentForecastViewModel
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastArgs
import com.arcmaksim.weatherapp.presentation.dailyforecast.DailyForecastScreen
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastArgs
import com.arcmaksim.weatherapp.presentation.hourlyforecast.HourlyForecastScreen
import com.arcmaksim.weatherapp.presentation.model.toArgsDto
import com.arcmaksim.weatherapp.presentation.model.toEntity

@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    val navigationComponent = DaggerNavigationComponent.create()

    val currentForecastRoute = navigationComponent.getCurrentForecastRoute()
    val hourlyForecastRoute = navigationComponent.getHourlyForecastRoute()
    val dailyForecastRoute = navigationComponent.getDailyForecastRoute()

    NavHost(
        navController,
        startDestination = currentForecastRoute.route,
    ) {
        composable(currentForecastRoute.route) {
            val viewModel: ICurrentForecastViewModel = daggerViewModel {
                DaggerForecastComponent.create().getCurrentForecastViewModel()
            }

            CurrentForecastScreen(
                viewModel = viewModel,
                showHourlyForecast = { timezone, records ->
                    dailyForecastRoute.navigateTo(
                        navController,
                        DailyForecastArgs(timezone, records.map { it.toArgsDto() })
                    )
                },
                showDailyForecast = { timezone, records ->
                    hourlyForecastRoute.navigateTo(
                        navController,
                        HourlyForecastArgs(timezone, records.map { it.toArgsDto() })
                    )
                }
            )
        }

        composable(dailyForecastRoute.baseRoute) { entry ->
            val args = dailyForecastRoute.parseArguments(entry)
            DailyForecastScreen(
                args.timezone,
                args.records.map { it.toEntity() },
            )
        }

        composable(hourlyForecastRoute.baseRoute) { entry ->
            val args = hourlyForecastRoute.parseArguments(entry)
            HourlyForecastScreen(
                args.timezone,
                args.records.map { it.toEntity() },
            )
        }
    }
}
