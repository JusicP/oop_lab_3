package com.lab.weather.service.api

import com.lab.weather.models.Weather

interface WeatherRepository {
    suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: String?): Weather
}