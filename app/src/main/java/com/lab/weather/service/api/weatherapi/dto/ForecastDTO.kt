package com.lab.weather.service.api.weatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Weather

data class ForecastDtoWeatherApi(
    @SerializedName("location") var location: Location? = null,
    @SerializedName("forecast") var forecast: Forecast? = null,
)
fun ForecastDtoWeatherApi.asDomainModel(): List<Weather> {
    TODO("Not implemented")
}

data class Forecast(
    @SerializedName("forecastday") var forecastDay: List<ForecastDay>? = null,
)

data class ForecastDay(
    @SerializedName("hour") var hour: List<Current>? = null,
)