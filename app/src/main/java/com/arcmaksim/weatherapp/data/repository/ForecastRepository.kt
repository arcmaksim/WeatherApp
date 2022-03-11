package com.arcmaksim.weatherapp.data.repository

import com.arcmaksim.weatherapp.data.api.ForecastApi
import com.arcmaksim.weatherapp.data.model.DailyForecastRecordDto
import com.arcmaksim.weatherapp.data.model.ForecastRecordDto
import com.arcmaksim.weatherapp.data.toEntity
import com.arcmaksim.weatherapp.domain.IForecastRepository
import com.arcmaksim.weatherapp.domain.model.Forecast
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.domain.model.ForecastRecordGroup
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi,
) : IForecastRepository {

    override suspend fun fetchForecast(
        apiKey: String,
        latitude: Double,
        longitude: Double,
    ): Forecast {
        val dto = forecastApi.getForecast(apiKey, latitude.toString(), longitude.toString())
        return Forecast(
            timezone = dto.timezone,
            current = dto.currently.toEntity(),
            hourlyForecast = ForecastRecordGroup(
                summary = dto.hourly.summary,
                type = dto.hourly.icon.toEntity(),
                records = dto.hourly.data.map { it.toEntity() }
            ),
            dailyForecast = ForecastRecordGroup(
                summary = dto.daily.summary,
                type = dto.daily.icon.toEntity(),
                records = dto.daily.data.map { it.toEntity() }
            ),
        )
    }

    private fun ForecastRecordDto.toEntity(): ForecastRecord = ForecastRecord(
        type = icon.toEntity(),
        time = time,
        temperature = temperature.toInt(),
        summary = summary,
        precipitationProbability = precipProbability.toInt(),
        apparentTemperature = apparentTemperature.toInt(),
        windGust = windGust.toInt(),
        windSpeed = windSpeed.toInt(),
        humidity = humidity.toInt(),
    )

    private fun DailyForecastRecordDto.toEntity(): ForecastRecord = ForecastRecord(
        type = icon.toEntity(),
        time = time,
        temperature = temperatureMax.toInt(),
        summary = summary,
        precipitationProbability = precipProbability.toInt(),
        apparentTemperature = apparentTemperatureMax.toInt(),
        windGust = windGust.toInt(),
        windSpeed = windSpeed.toInt(),
        humidity = humidity.toInt(),
    )

}
