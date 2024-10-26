package com.lab.weather.service.api.openweatherapi

import com.lab.weather.models.Weather
import com.lab.weather.service.api.WeatherRepository
import com.lab.weather.service.api.openweatherapi.dto.asDomainModel

class OpenWeatherRepositoryImpl() : WeatherRepository {
    private var api: OpenWeatherApi = RetrofitClient.getClient("https://api.openweathermap.org/").create(OpenWeatherApi::class.java)

    override suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: String?): Weather {
        val weather = api.getCurrentWeather(apiKey, lang, location, units)
        return weather.asDomainModel()
    }
}