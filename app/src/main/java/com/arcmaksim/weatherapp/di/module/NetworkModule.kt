package com.arcmaksim.weatherapp.di.module

import com.arcmaksim.weatherapp.data.api.ForecastApi
import com.arcmaksim.weatherapp.data.model.WeatherTypeDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    @Reusable
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(
            WeatherTypeDto::class.java,
            EnumJsonAdapter.create(WeatherTypeDto::class.java).withUnknownFallback(WeatherTypeDto.ClearDay),
        )
        .build()

    @Provides
    @Reusable
    fun provideRetrofit(
        httpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.darksky.net/")
        .build()

    @Provides
    fun provideForecastApi(
        retrofit: Retrofit,
    ): ForecastApi = retrofit.create(ForecastApi::class.java)

}
