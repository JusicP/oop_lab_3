package com.lab.weather.shared

enum class WeatherRepositoryType {
    WEATHER_API, OPEN_WEATHER_API;
}

enum class TemperatureMeasureUnit {
    CELSIUS, KELVIN, FAHRENHEIT;
}

enum class UnitsOfMeasurement(val unit: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial");
}

interface AppPreferences {
    var weatherRepositoryType: WeatherRepositoryType
    var openWeatherApiKey: String
    var weatherApiKey: String
    var unitsOfMeasurement: UnitsOfMeasurement // openweathermap
    var temperatureUnit: TemperatureMeasureUnit
    var locale: String

    fun getCurrentApiKey(): String?
}