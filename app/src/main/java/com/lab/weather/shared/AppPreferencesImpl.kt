package com.lab.weather.shared

import android.content.SharedPreferences
import java.util.Locale

object AppPreferencesImpl : AppPreferences {
    private var sharedPreferences: SharedPreferences? = null

    fun setup(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    override var weatherRepositoryType: WeatherRepositoryType
        get() = WeatherRepositoryType.valueOf(sharedPreferences!!.getString("weatherRepositoryType",
            WeatherRepositoryType.OPEN_WEATHER_API.toString()
        ).toString())
        set(value) {}
    override var openWeatherApiKey: String
        get() = sharedPreferences!!.getString("openWeatherApiKey", "").toString()
        set(value) {}
    override var weatherApiKey: String
        get() = sharedPreferences!!.getString("weatherApiKey", "").toString()
        set(value) {}
    override var temperatureUnit: TemperatureMeasureUnit
        get() = TemperatureMeasureUnit.valueOf(sharedPreferences!!.getString("temperatureUnit", TemperatureMeasureUnit.CELSIUS.toString()).toString())
        set(value) {}
    override var unitsOfMeasurement: UnitsOfMeasurement
        get() = UnitsOfMeasurement.valueOf(sharedPreferences!!.getString("unitsOfMeasurement", UnitsOfMeasurement.STANDARD.toString()).toString())
        set(value) {}
    override var locale: String
        get() = Locale.getDefault().language
        set(value) {}

    override fun getCurrentApiKey(): String {
        if (weatherRepositoryType == WeatherRepositoryType.WEATHER_API) {
            return weatherApiKey
        } else if (weatherRepositoryType == WeatherRepositoryType.OPEN_WEATHER_API) {
            return openWeatherApiKey
        }
        throw NotImplementedError()
    }
}