package com.lab.weather.service.api

import com.lab.weather.models.Geocoding
import com.lab.weather.models.Weather
import com.lab.weather.shared.UnitsOfMeasurement

interface WeatherRepository {
    suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): Weather
    suspend fun getGeocoding(apiKey: String?, lang: String?, location: String?): List<Geocoding>
    suspend fun getForecast(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): List<Weather>
}