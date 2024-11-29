package com.lab.weather.main.ui.widget

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lab.weather.service.WeatherService
import com.lab.weather.shared.AppPreferencesImpl
import kotlinx.coroutines.*

class WeatherWidgetWorker (private val context: Context, parameters: WorkerParameters) : CoroutineWorker(context, parameters) {
    val service = WeatherService(AppPreferencesImpl)

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    companion object {
        const val WORK_NAME = "WidgetUpdateWorker"
    }

    override suspend fun doWork(): Result {
        try {
            Log.e("WIDGET", "doWork called")
            requestFavouriteCity()
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }

    private fun requestFavouriteCity() {
        if (AppPreferencesImpl.favouriteCity == null) {
            Log.e("WIDGET", "null fav city")
        } else {
            updateWeather(AppPreferencesImpl.favouriteCity!!)
        }
    }

    private fun updateWeather(location: String) {
        Log.e("WIDGET", "updateWeather called")

        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                val weather = service.getCurrentWeather(location)
                Log.e("WIDGET", "%s".format(weather.city))

                WeatherWidget.notifyAppWidgetViewDataChanged(context, weather)
            }
        }
    }
}