package com.lab.weather.service.api.weatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Weather

data class WeatherDtoWeatherApi (
    @SerializedName("location") var location: Location? = null,
    @SerializedName("current") var current: Current? = null
)

fun WeatherDtoWeatherApi.asDomainModel(): Weather {
    // TODO: check units
    return Weather(
        city = location!!.name,
        description = current!!.condition!!.text,
        timezone = 0,
        dateTime = location!!.localtimeEpoch,
        temperature = current!!.tempCelsius,
        pressure = current!!.pressureMb,
        humidity = current!!.humidity,
        sunRize = 0,
        sunSet = 0,
        feelsLikeTemp = current!!.feelslikeCelsius,
        windSpeed = current!!.windMph
    )
}

data class Location (
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("localtime_epoch") var localtimeEpoch: Int? = null
)

data class Current (
    @SerializedName("last_updated_epoch") var lastUpdatedEpoch: Int? = null,
    @SerializedName("temp_c") var tempCelsius: Float? = null,
    @SerializedName("temp_f") var tempFahrenheit: Float? = null,
    @SerializedName("condition") var condition: Condition? = null,
    @SerializedName("wind_mph") var windMph: Float? = null,
    @SerializedName("wind_kph") var windKph: Float? = null,
    @SerializedName("pressure_mb") var pressureMb: Float? = null,
    @SerializedName("pressure_in") var pressureIn: Float? = null,
    @SerializedName("humidity") var humidity: Float? = null,
    @SerializedName("feelslike_c") var feelslikeCelsius: Float? = null,
    @SerializedName("feelslike_f") var feelslikeFahrenheit: Float? = null
)

data class Condition (
    @SerializedName("text") var text: String? = null,
)
