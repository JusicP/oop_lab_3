package com.lab.weather.service.api.weatherapi

import com.lab.weather.service.api.weatherapi.dto.ForecastDtoWeatherApi
import com.lab.weather.service.api.weatherapi.dto.GeocodingDtoWeatherApi
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

    @GET("search.json")
    suspend fun getGeocoding(
        @Query("key") apiKey: String?,
        @Query("q") location: String?,
    ): List<GeocodingDtoWeatherApi>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
        @Query("days") days: Int?, // for free plan 3 days is max
    ): ForecastDtoWeatherApi
}