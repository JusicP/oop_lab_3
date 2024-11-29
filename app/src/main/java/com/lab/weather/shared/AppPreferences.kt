package com.lab.weather.shared

enum class WeatherRepositoryType {
    WEATHER_API, OPEN_WEATHER_API;
}

enum class TemperatureMeasureUnit {
    CELSIUS, KELVIN, FAHRENHEIT;
}

enum class SpeedMeasureUnit {
    KM_PER_HOUR, MILE_PER_HOUR;
}

enum class PressureMeasureUnit {
    MILLIBAR, INCH;
}

enum class UnitsOfMeasurementSystem(val unit: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial");
}

data class UnitsOfMeasurement (
    var system: UnitsOfMeasurementSystem, // openweathermap
    var tempUnit: TemperatureMeasureUnit,
    var windSpeedUnit: SpeedMeasureUnit,
    var pressureUnit: PressureMeasureUnit
)

fun UnitsOfMeasurement.formatTemperatureUnit(api: WeatherRepositoryType): String {
    if (api.equals(WeatherRepositoryType.OPEN_WEATHER_API)) {
        if (system == UnitsOfMeasurementSystem.STANDARD) {
            return "K"
        } else if (system == UnitsOfMeasurementSystem.METRIC) {
            return "°С"
        } else if (system == UnitsOfMeasurementSystem.IMPERIAL) {
            return "°F"
        }
    } else {
        if (tempUnit == TemperatureMeasureUnit.CELSIUS) {
            return "°С"
        } else if (tempUnit == TemperatureMeasureUnit.FAHRENHEIT) {
            return "°F"
        }
    }

    return ""
}

fun UnitsOfMeasurement.formatWindSpeedUnit(api: WeatherRepositoryType): String {
    if (api.equals(WeatherRepositoryType.OPEN_WEATHER_API)) {
        if (system == UnitsOfMeasurementSystem.STANDARD) {
            return "m/s"
        } else if (system == UnitsOfMeasurementSystem.METRIC) {
            return "m/s"
        } else if (system == UnitsOfMeasurementSystem.IMPERIAL) {
            return "mph"
        }
    } else {
        if (windSpeedUnit == SpeedMeasureUnit.KM_PER_HOUR) {
            return "km/h"
        } else if (windSpeedUnit == SpeedMeasureUnit.MILE_PER_HOUR) {
            return "mph"
        }
    }

    return ""
}

fun UnitsOfMeasurement.formatPressureUnit(api: WeatherRepositoryType): String {
    if (api.equals(WeatherRepositoryType.OPEN_WEATHER_API)) {
        return "hPa"
    } else {
        if (pressureUnit == PressureMeasureUnit.MILLIBAR) {
            return "mbar"
        } else if (pressureUnit == PressureMeasureUnit.INCH) {
            return "inHg"
        }
    }

    return ""
}

interface AppPreferences {
    var weatherRepositoryType: WeatherRepositoryType
    var openWeatherApiKey: String
    var weatherApiKey: String
    var unitsOfMeasurement: UnitsOfMeasurement // openweathermap
    var locale: String

    var favouriteCity: String?
    var featuredCities: Set<String>

    fun getCurrentApiKey(): String?
}