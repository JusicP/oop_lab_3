package com.lab.weather.main.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.lab.weather.R
import com.lab.weather.databinding.FragmentCityPreviewBinding
import com.lab.weather.main.MainActivity
import com.lab.weather.main.ui.Status
import com.lab.weather.models.Weather
import com.lab.weather.service.WeatherService
import com.lab.weather.shared.AppPreferencesImpl
import com.lab.weather.shared.formatPressureUnit
import com.lab.weather.shared.formatTemperatureUnit
import com.lab.weather.shared.formatWindSpeedUnit
import java.text.SimpleDateFormat
import java.util.*

class CityPreviewFragment : Fragment() {

    private var _binding: FragmentCityPreviewBinding? = null
    private lateinit var viewModel: CityPreviewViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityPreviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val city = requireArguments().getString("city")

        viewModel = ViewModelProvider(
            this,
            CityPreviewViewModelFactory(WeatherService(AppPreferencesImpl)),
        ).get(CityPreviewViewModel::class.java)

        observeWeather()

        viewModel.getWeather(city!!)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeWeather() {
        viewModel.weatherLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onWeather(it.data)
                Status.ERROR -> onError(it.error)
            }
        })
    }


    private fun onWeather(weather: Weather?) {
        if (weather == null) {
            onError(getString(R.string.no_weather_data))
            return
        }

        setLoadingVisible(false)
        setErrorVisible(false)
        setWeatherVisible(true)

        binding.cityName.text = weather.city
        binding.temperature.text = weather.temperature.toString()

        val tempUnit = weather.units.formatTemperatureUnit(weather.api)

        binding.unit.text = tempUnit

        val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        val time = formatter.format(weather.dateTime!! * 1000L).toString()

        val speedUnit = weather.units.formatWindSpeedUnit(weather.api)
        val pressureUnit = weather.units.formatPressureUnit(weather.api)

        binding.weatherDetails.humidityLabel.text = getString(R.string.detail_humidity).format(weather.humidity)
        binding.weatherDetails.tempFeelsLabel.text = getString(R.string.detail_temp_feels).format(weather.feelsLikeTemp, tempUnit)
        binding.weatherDetails.pressureLabel.text = getString(R.string.detail_pressure).format(weather.pressure, pressureUnit)

        binding.weatherDetails.visibilityLabel.visibility = if (weather.visibility == null) View.GONE else View.VISIBLE
        binding.weatherDetails.visibilityLabel.text = getString(R.string.detail_visibility).format(weather.visibility)
        binding.weatherDetails.windSpeedLabel.text = getString(R.string.detail_wind_speed).format(weather.windSpeed, speedUnit)

        binding.weatherDetails.updatedAtLabel.text = getString(R.string.last_updated).format(time)
    }

    private fun onLoading() {
        setLoadingVisible(true)
        setErrorVisible(false)
        setWeatherVisible(false)
    }

    private fun onError(msg: String?) {
        setLoadingVisible(false)
        setErrorVisible(true, msg)
        setWeatherVisible(false)
    }


    private fun setLoadingVisible(state: Boolean) {
        if (state) {
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.visibility = View.GONE
        }
    }

    private fun setWeatherVisible(state: Boolean) {
        if (state) {
            binding.weather.visibility = View.VISIBLE
        } else {
            binding.weather.visibility = View.GONE
        }
    }
    private fun setErrorVisible(state: Boolean, msg: String? = null) {
        if (state) {
            binding.errorText.visibility = View.VISIBLE
            binding.errorText.text = msg
        } else {
            binding.errorText.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        val activity = requireActivity() as MainActivity
        activity.setRefreshBtnVisible(false)
        activity.setFavouriteBtnVisible(true)
    }
}