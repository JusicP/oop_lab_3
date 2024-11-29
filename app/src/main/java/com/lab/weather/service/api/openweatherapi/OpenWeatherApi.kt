package com.lab.weather.service.api.openweatherapi

import com.lab.weather.service.api.openweatherapi.dto.ForecastDtoOpenWeatherMap
import com.lab.weather.service.api.openweatherapi.dto.GeocodingDtoOpenWeatherMap
import com.lab.weather.service.api.openweatherapi.dto.WeatherDtoOpenWeatherMap
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
     suspend fun getCurrentWeather(
        @Query("appid") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
        @Query("units") units: String?
    ): WeatherDtoOpenWeatherMap

    @GET("geo/1.0/direct")
    suspend fun getGeocoding(
        @Query("appid") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
    ): List<GeocodingDtoOpenWeatherMap>

    @GET("data/2.5/forecast")
    suspend fun getForecast( // for free plan 5 days is max
        @Query("appid") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("q") location: String?,
        @Query("units") units: String?
    ): ForecastDtoOpenWeatherMap
}