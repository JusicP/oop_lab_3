package com.lab.weather.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.lab.weather.service.WeatherService
import com.lab.weather.shared.AppPreferences
import kotlinx.coroutines.*

class WeatherViewModel(preferences: AppPreferences) : ViewModel() {
    private var service: WeatherService = WeatherService(preferences)

    // use coroutine exception handler to avoid using try/catch everywhere
    val coroutineExceptionHanlder = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
        onError(throwable.message.toString())
    }

    val errorMessage = MutableLiveData<String>()

    // check if we are connected to the Internet
    fun isConnectionAvailable(context: Context): Boolean {
        return true
    }

    fun getWeather() {
        var job = CoroutineScope(Dispatchers.Main + coroutineExceptionHanlder).launch {
            val weather = service.getCurrentWeather("Kyiv")
            withContext(Dispatchers.Main) {
                onError("Got weather (maybe)")
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}

// use factory to allow dependency injection
class WeatherViewModelFactory(
    private val preferences: AppPreferences,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(
                preferences = preferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
