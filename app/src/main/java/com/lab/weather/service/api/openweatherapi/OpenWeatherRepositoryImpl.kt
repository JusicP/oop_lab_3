package com.lab.weather.service.api.openweatherapi

import com.lab.weather.models.Geocoding
import com.lab.weather.models.Weather
import com.lab.weather.service.api.WeatherRepository
import com.lab.weather.service.api.openweatherapi.dto.asDomainModel
import com.lab.weather.shared.UnitsOfMeasurement

class OpenWeatherRepositoryImpl() : WeatherRepository {
    private var api: OpenWeatherApi = RetrofitClient.getClient("https://api.openweathermap.org/").create(OpenWeatherApi::class.java)

    override suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): Weather {
        val weather = api.getCurrentWeather(apiKey, lang, location, units.system.toString())
        return weather.asDomainModel(units)
    }

    override suspend fun getGeocoding(apiKey: String?, lang: String?, location: String?): List<Geocoding> {
        val geo = api.getGeocoding(apiKey, lang, location)
        return geo.map{ geocoding ->
            geocoding.asDomainModel()
        }
    }

    override suspend fun getForecast(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): List<Weather> {
        val forecast = api.getForecast(apiKey, lang, location, units.system.toString())
        return forecast.asDomainModel(units)
    }
}