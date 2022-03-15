package com.arcmaksim.weatherapp.domain.model

import java.text.SimpleDateFormat
import java.util.TimeZone

class ForecastRecord(
    val time: Long,
    val summary: String,
    val type: WeatherType,
    val temperature: Int,
    val precipitationProbability: Int,
    val apparentTemperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val windGust: Int,
) {

    fun getFormattedTime(
        timezone: String,
    ): String {
        val formatter = SimpleDateFormat("H").apply {
            timeZone = TimeZone.getTimeZone(timezone)
        }
        return StringBuilder(formatter.format(time * 1000))
            .append(":00")
            .toString()
    }

    fun getDayOfTheWeek(
        timezone: String,
    ): String = SimpleDateFormat("EEEE").apply {
        timeZone = TimeZone.getTimeZone(timezone)
    }.format(time * 1000)

}
