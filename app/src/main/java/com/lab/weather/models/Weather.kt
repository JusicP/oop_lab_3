package com.lab.weather.models

import com.lab.weather.shared.UnitsOfMeasurement
import com.lab.weather.shared.WeatherRepositoryType

// current weather state
data class Weather (
    var api: WeatherRepositoryType,
    var units: UnitsOfMeasurement,
    var city: String? = null,
    var description: String? = null,
    var timezone: Int? = null,
    var dateTime: Int? = null, // timestamp
    var sunRize: Int? = null, // timestamp
    var sunSet: Int? = null, // timestamp
    var temperature: Float? = null,
    var pressure: Float? = null,
    var humidity: Float? = null,
    var feelsLikeTemp: Float? = null,
    var visibility: Float? = null,
    var windSpeed: Float? = null,
)