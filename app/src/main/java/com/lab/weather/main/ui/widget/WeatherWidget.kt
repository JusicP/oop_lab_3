package com.lab.weather.main.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.lab.weather.R
import com.lab.weather.models.Weather
import com.lab.weather.shared.formatTemperatureUnit
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        setupRecurringWork(context)
        Log.e("WIDGET", "onEnabled called")
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.e("WIDGET", "onDisabled called")

        WorkManager.getInstance(context).cancelUniqueWork(WeatherWidgetWorker.WORK_NAME)

    }

    private fun setupRecurringWork(context: Context) {
        val repeatingRequest =
            PeriodicWorkRequestBuilder<WeatherWidgetWorker>(15, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WeatherWidgetWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            weather: Weather? = null
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget)

            if (weather != null) {
                val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
                val time = formatter.format(weather.dateTime!! * 1000L)

                views.setTextViewText(R.id.city_name, weather.city)
                views.setTextViewText(R.id.temperature, weather.temperature?.toInt().toString())
                views.setTextViewText(R.id.unit, weather.units.formatTemperatureUnit(weather.api))
                views.setTextViewText(R.id.last_updated, context.getText(R.string.last_updated).toString().format(time))
            } else {
                views.setTextViewText(R.id.city_name, "You haven't chosen your favorite city")
            }

//            val intent = Intent(context, MainActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(
//                context,
//                appWidgetId,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun notifyAppWidgetViewDataChanged(context: Context, weather: Weather) {
            val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(
                    context,
                    WeatherWidget::class.java
                )
            )
            for (appWidgetId in appWidgetIds)
                updateAppWidget(
                    context,
                    appWidgetManager,
                    appWidgetId,
                    weather
                )
        }
    }
}