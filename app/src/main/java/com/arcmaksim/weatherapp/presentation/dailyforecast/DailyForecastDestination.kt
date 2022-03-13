package com.arcmaksim.weatherapp.presentation.dailyforecast

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.presentation.ParcelableArrayNavType
import com.google.gson.Gson

private const val destinationName = "dailyForecast"
private const val timezoneKey = "timezone"
private const val recordsKey = "records"

class DailyForecastDestination {

    class Args(
        val timezone: String,
        val records: Array<ForecastRecord>,
    )

    companion object {
        fun parseArguments(
            backStackEntry: NavBackStackEntry,
        ): Args {
            val timezone = backStackEntry.arguments?.getString(timezoneKey)!!
            val records = Gson().fromJson(
                backStackEntry.arguments?.getString(recordsKey),
                Array<ForecastRecord>::class.java,
            )
            return Args(
                timezone = timezone,
                records = records,
            )
        }

        val argumentList: MutableList<NamedNavArgument>
            get() = mutableListOf(
                navArgument(timezoneKey) {
                    type = NavType.StringType
                },
                navArgument(recordsKey) {
                    type = ParcelableArrayNavType<ForecastRecord>()
                },
            )

        fun getDestination(
            timezone: String,
            records: Array<ForecastRecord>,
        ): String = "$destinationName?$timezoneKey=$timezone,$recordsKey=${Uri.encode(Gson().toJson(records))}"

        val route
            get() = "$destinationName?$timezoneKey={$timezoneKey},$recordsKey={$recordsKey}"
    }

}
