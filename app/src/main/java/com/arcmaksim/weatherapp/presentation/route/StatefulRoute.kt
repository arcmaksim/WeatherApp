package com.arcmaksim.weatherapp.presentation.route

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.arcmaksim.weatherapp.data.serializer.ArgsDtoSerializer

private const val argsKey = "args"

abstract class StatefulRoute<T : Any>(
    private val argsDtoSerializer: ArgsDtoSerializer,
    private val route: String,
    private val argsClass: Class<T>,
) {

    val baseRoute: String
        get() = "$route?$argsKey={$argsKey}"

    fun navigateTo(
        navController: NavController,
        args: T,
    ) {
        val args = argsDtoSerializer.serialize(argsClass, args)
        navController.navigate("$route?$argsKey=${Uri.encode(args)}")
    }

    fun parseArguments(
        backStackEntry: NavBackStackEntry,
    ): T = argsDtoSerializer.deserialize(
        argsClass,
        requireNotNull(backStackEntry.arguments?.getString(argsKey)),
    )

}
