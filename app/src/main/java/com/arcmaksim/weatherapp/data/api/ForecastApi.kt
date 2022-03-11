package com.arcmaksim.weatherapp.data.api

import com.arcmaksim.weatherapp.data.model.ForecastDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ForecastApi {

    @GET("forecast/{apiKey}/{latitude},{longitude}?exclude=minutely,alerts,flags&units=ca")
    suspend fun getForecast(
        @Path(value = "apiKey") apiKey: String,
        @Path(value = "latitude") latitude: String,
        @Path(value = "longitude") longitude: String,
    ): ForecastDto

}
