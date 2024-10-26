package com.lab.weather.service.api.weatherapi

import com.lab.weather.service.api.weatherapi.dto.WeatherDtoWeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
    ): WeatherDtoWeatherApi
}