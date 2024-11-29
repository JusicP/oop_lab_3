package com.lab.weather.shared

import android.content.SharedPreferences
import java.util.*

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
    override var unitsOfMeasurement: UnitsOfMeasurement
        get() = UnitsOfMeasurement(
                UnitsOfMeasurementSystem.valueOf(sharedPreferences!!.getString("unitsOfMeasurement", UnitsOfMeasurementSystem.STANDARD.toString()).toString()),
                TemperatureMeasureUnit.valueOf(sharedPreferences!!.getString("temperatureUnit", TemperatureMeasureUnit.CELSIUS.toString()).toString()),
                SpeedMeasureUnit.valueOf(sharedPreferences!!.getString("windSpeedUnit", SpeedMeasureUnit.KM_PER_HOUR.toString()).toString()),
                PressureMeasureUnit.valueOf(sharedPreferences!!.getString("pressureUnit", PressureMeasureUnit.MILLIBAR.toString()).toString())
        )
        set(value) {}
    override var locale: String
        get() = Locale.getDefault().language
        set(value) {}

    override var favouriteCity: String?
        get() {
            return sharedPreferences!!.getString("favouriteCity", null)
        }
        set(value) {
            val editor = sharedPreferences!!.edit()
            editor.putString("favouriteCity", value)
            editor.apply()
        }

    override var featuredCities: Set<String>
        get() {
            val set = sharedPreferences!!.getStringSet("featuredCities", null)
            if (set == null) {
                return emptySet<String>()
            }
            return set
        }
        set(value) {
            val editor = sharedPreferences!!.edit()
            editor.putStringSet("featuredCities", value)
            editor.apply()
        }

    override fun getCurrentApiKey(): String {
        if (weatherRepositoryType == WeatherRepositoryType.WEATHER_API) {
            return weatherApiKey
        } else if (weatherRepositoryType == WeatherRepositoryType.OPEN_WEATHER_API) {
            return openWeatherApiKey
        }
        throw NotImplementedError()
    }
}