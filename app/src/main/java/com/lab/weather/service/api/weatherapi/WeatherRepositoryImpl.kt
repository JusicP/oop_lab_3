package com.lab.weather.service.api.weatherapi

import com.lab.weather.models.Geocoding
import com.lab.weather.models.Weather
import com.lab.weather.service.api.WeatherRepository
import com.lab.weather.service.api.weatherapi.dto.asDomainModel
import com.lab.weather.shared.UnitsOfMeasurement

class WeatherRepositoryImpl() : WeatherRepository {
    private var api: WeatherApi = RetrofitClient.getClient("https://api.weatherapi.com/v1/").create(WeatherApi::class.java)

    override suspend fun getCurrentWeather(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): Weather {
        val weather = api.getCurrentWeather(apiKey, lang, location)
        return weather.asDomainModel(units)
    }

    override suspend fun getGeocoding(apiKey: String?, lang: String?, location: String?): List<Geocoding> {
        val geo = api.getGeocoding(apiKey, location)
        return geo.map{ geocoding ->
            geocoding.asDomainModel()
        }
    }

    override suspend fun getForecast(apiKey: String?, lang: String?, location: String?, units: UnitsOfMeasurement): List<Weather> {
        val forecast = api.getForecast(apiKey, lang, location, 4)
        return forecast.asDomainModel()
    }
}