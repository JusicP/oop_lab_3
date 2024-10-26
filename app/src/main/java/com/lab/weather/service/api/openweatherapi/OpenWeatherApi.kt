package com.lab.weather.service.api.openweatherapi

import com.lab.weather.service.api.openweatherapi.dto.WeatherDtoOpenWeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
     suspend fun getCurrentWeather(
        @Query("appid") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
        @Query("units") units: String?
    ): WeatherDtoOpenWeatherApi
}