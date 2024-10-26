package com.lab.weather.service.api

import com.lab.weather.service.api.openweatherapi.OpenWeatherRepositoryImpl
import com.lab.weather.service.api.weatherapi.WeatherRepositoryImpl
import com.lab.weather.shared.WeatherRepositoryType

object WeatherRepositoryFactory {
    fun createWeatherRepository(repoType: WeatherRepositoryType): WeatherRepository {
        if (repoType == WeatherRepositoryType.WEATHER_API) {
            return WeatherRepositoryImpl()
        } else if (repoType == WeatherRepositoryType.OPEN_WEATHER_API) {
            return OpenWeatherRepositoryImpl()
        }

        throw NotImplementedError()
    }
}