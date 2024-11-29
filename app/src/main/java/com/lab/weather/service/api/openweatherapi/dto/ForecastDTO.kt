package com.lab.weather.service.api.openweatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Weather
import com.lab.weather.shared.UnitsOfMeasurement

data class ForecastDtoOpenWeatherMap(
    @SerializedName("list") var weatherForecast: List<WeatherDtoOpenWeatherMap>? = null,
)
fun ForecastDtoOpenWeatherMap.asDomainModel(units: UnitsOfMeasurement): List<Weather> {
    return weatherForecast!!.map{ weather ->
        weather.asDomainModel(units)
    }
}