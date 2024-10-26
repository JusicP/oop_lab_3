package com.lab.weather.service

import com.lab.weather.models.Weather
import com.lab.weather.service.api.WeatherRepositoryFactory
import com.lab.weather.shared.AppPreferences

// weather service which is the link between the client and the weather API
class WeatherService(private val preferences: AppPreferences) {
    suspend fun getCurrentWeather(location: String): Weather {
        val repo = WeatherRepositoryFactory.createWeatherRepository(preferences.weatherRepositoryType);

        return repo.getCurrentWeather(preferences.getCurrentApiKey(), preferences.locale, location, preferences.unitsOfMeasurement.toString())
    }
}