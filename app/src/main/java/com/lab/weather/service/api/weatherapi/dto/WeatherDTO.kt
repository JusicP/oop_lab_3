package com.lab.weather.service.api.weatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Weather
import com.lab.weather.shared.*

data class WeatherDtoWeatherApi (
    @SerializedName("location") var location: Location? = null,
    @SerializedName("current") var current: Current? = null
)

fun WeatherDtoWeatherApi.asDomainModel(units: UnitsOfMeasurement): Weather {
    return Weather(
        api = WeatherRepositoryType.WEATHER_API,
        units = units,
        city = location!!.name,
        description = current!!.condition!!.text,
        timezone = 0,
        dateTime = location!!.localtimeEpoch,
        temperature = temperatureByUnit(units),
        pressure = pressureByUnit(units),
        humidity = current!!.humidity,
        sunRize = 0,
        sunSet = 0,
        feelsLikeTemp = feelTemperatureByUnit(units),
        windSpeed = windSpeedByUnit(units)
    )
}

fun WeatherDtoWeatherApi.temperatureByUnit(units: UnitsOfMeasurement): Float? {
    if (units.tempUnit == TemperatureMeasureUnit.CELSIUS) {
        return current!!.tempCelsius
    } else if (units.tempUnit == TemperatureMeasureUnit.FAHRENHEIT) {
        return current!!.tempFahrenheit
    } else {
        return current!!.tempCelsius
    }
}

fun WeatherDtoWeatherApi.feelTemperatureByUnit(units: UnitsOfMeasurement): Float? {
    if (units.tempUnit == TemperatureMeasureUnit.CELSIUS) {
        return current!!.feelslikeCelsius
    } else if (units.tempUnit == TemperatureMeasureUnit.FAHRENHEIT) {
        return current!!.feelslikeFahrenheit
    } else {
        return current!!.feelslikeCelsius
    }
}

fun WeatherDtoWeatherApi.pressureByUnit(units: UnitsOfMeasurement): Float? {
    if (units.pressureUnit == PressureMeasureUnit.MILLIBAR) {
        return current!!.pressureMb
    } else if (units.pressureUnit == PressureMeasureUnit.INCH) {
        return current!!.pressureIn
    } else {
        return current!!.pressureMb
    }
}

fun WeatherDtoWeatherApi.windSpeedByUnit(units: UnitsOfMeasurement): Float? {
    if (units.windSpeedUnit == SpeedMeasureUnit.KM_PER_HOUR) {
        return current!!.windKph
    } else if (units.windSpeedUnit == SpeedMeasureUnit.MILE_PER_HOUR) {
        return current!!.windMph
    } else {
        return current!!.windKph
    }
}

data class Location (
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("localtime_epoch") var localtimeEpoch: Int? = null
)

data class Current (
    @SerializedName("last_updated_epoch") var lastUpdatedEpoch: Int? = null,
    @SerializedName("time_epoch") var timeEpoch: Int? = null,
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
