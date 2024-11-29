package com.lab.weather.service.api.openweatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Weather
import com.lab.weather.shared.UnitsOfMeasurement
import com.lab.weather.shared.WeatherRepositoryType

data class WeatherDtoOpenWeatherMap(
    @SerializedName("weather") var weather: List<WeatherGeneralStatus>? = null,
    @SerializedName("main") var main: Main? = null,
    @SerializedName("sys") var sys: Sys? = null,
    @SerializedName("wind") var wind: Wind? = null,

    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("visibility") var visibility: Float? = null,
    @SerializedName("dt") var timestamp: Int? = null,
    @SerializedName("name") var name: String? = null
)
fun WeatherDtoOpenWeatherMap.asDomainModel(units: UnitsOfMeasurement): Weather {
    return Weather(
        api = WeatherRepositoryType.OPEN_WEATHER_API,
        units = units,
        city = name,
        description = weather!![0].description,
        dateTime = timestamp,
        temperature = main!!.temp,
        pressure = main!!.pressure,
        humidity = main!!.humidity,
        sunRize = sys!!.sunrise,
        sunSet = sys!!.sunset,
        feelsLikeTemp = main!!.feelsLike,
        windSpeed = wind!!.speed
    )
}

data class WeatherGeneralStatus (
    @SerializedName("description") var description: String? = null,
)

data class Main (
    @SerializedName("temp") var temp: Float? = null,
    @SerializedName("feels_like") var feelsLike: Float? = null,
    @SerializedName("temp_min") var tempMin: Float? = null,
    @SerializedName("temp_max") var tempMax: Float? = null,
    @SerializedName("pressure") var pressure: Float? = null,
    @SerializedName("humidity") var humidity: Float? = null
)

data class Sys (
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null
)

data class Wind (
    @SerializedName("speed") var speed: Float? = null,
)