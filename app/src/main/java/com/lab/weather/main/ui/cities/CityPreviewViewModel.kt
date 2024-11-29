package com.lab.weather.main.ui.cities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lab.weather.main.ui.BaseViewModel
import com.lab.weather.main.ui.Event
import com.lab.weather.models.Weather
import com.lab.weather.service.WeatherService

class CityPreviewViewModel(
    private val service: WeatherService,
) : BaseViewModel() {

    val weatherLiveData = MutableLiveData<Event<Weather>>()

    fun getWeather(location: String) {
        requestWithLiveData(weatherLiveData) {
            service.getCurrentWeather(location)
        }
    }
}

// use factory to allow dependency injection
class CityPreviewViewModelFactory(
    private val service: WeatherService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityPreviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityPreviewViewModel(
                service = service
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
