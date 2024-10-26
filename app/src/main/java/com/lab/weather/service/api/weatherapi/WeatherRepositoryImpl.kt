package com.lab.weather.service.api.weatherapi

import com.lab.weather.models.Weather
import com.lab.weather.service.api.WeatherRepository
import com.lab.weather.service.api.weatherapi.dto.asDomainModel

class WeatherRepositoryImpl() : WeatherRepository {
    private var api: WeatherApi = RetrofitClient.getClient("https://api.weatherapi.com/v1/").create(WeatherApi::class.java)

    override suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: String?): Weather {
        val weather = api.getCurrentWeather(apiKey, lang, location)
        return weather.asDomainModel()
    }
}