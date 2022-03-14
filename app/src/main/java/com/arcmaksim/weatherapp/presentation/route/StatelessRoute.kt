package com.arcmaksim.weatherapp.presentation.route

import androidx.navigation.NavController

abstract class StatelessRoute(
    val route: String,
) {

    fun navigateTo(
        navController: NavController,
    ) {
        navController.navigate(route)
    }

}
