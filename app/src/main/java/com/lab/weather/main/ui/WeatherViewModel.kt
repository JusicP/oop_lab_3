package com.lab.weather.main.ui

import androidx.lifecycle.*
import com.lab.weather.models.Geocoding
import com.lab.weather.models.Weather

import com.lab.weather.service.WeatherService
import com.lab.weather.shared.AppPreferencesImpl
import kotlinx.coroutines.*

class WeatherViewModel(
    private val service: WeatherService,
) : BaseViewModel() {

    val weatherLiveData = MutableLiveData<Event<Weather>>()
    val geoLiveData = MutableLiveData<Event<List<Geocoding>>>()
    val favCityLiveData = MutableLiveData<String?>()
    val featuredCitiesLiveData = MutableLiveData<Set<String>>()

    init {
        getFavouriteCity()
    }

    fun addFeaturedCity(location: String) {
        if (AppPreferencesImpl.favouriteCity == null)
            setFavouriteCity(location)

        val featuredCities = AppPreferencesImpl.featuredCities.plus(location)
        AppPreferencesImpl.featuredCities = featuredCities
        featuredCitiesLiveData.postValue(AppPreferencesImpl.featuredCities)
    }

    fun deleteFeaturedCity(location: String): Set<String> {
        if (AppPreferencesImpl.favouriteCity.equals(location))
            setFavouriteCity(null)

        val featuredCities = AppPreferencesImpl.featuredCities.minus(location)
        AppPreferencesImpl.featuredCities = featuredCities
        featuredCitiesLiveData.postValue(AppPreferencesImpl.featuredCities)
        return featuredCities
    }

    fun setFavouriteCity(location: String?) {
        AppPreferencesImpl.favouriteCity = location
        favCityLiveData.postValue(location)

        if (location != null)
            addFeaturedCity(location)
    }

    fun getFavouriteCity() {
        favCityLiveData.postValue(AppPreferencesImpl.favouriteCity)
    }

    fun getFeaturedCities() {
        featuredCitiesLiveData.postValue(AppPreferencesImpl.featuredCities)
    }

    fun getWeather(location: String) {
        requestWithLiveData(weatherLiveData) {
            service.getCurrentWeather(location)
        }
    }

    fun getGeocoding(location: String) {
        requestWithLiveData(geoLiveData) {
            service.getGeocoding(location)
        }
    }
}

// use factory to allow dependency injection
class WeatherViewModelFactory(
    private val service: WeatherService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(
                service = service,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
