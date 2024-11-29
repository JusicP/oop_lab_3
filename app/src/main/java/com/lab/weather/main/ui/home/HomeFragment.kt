package com.lab.weather.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lab.weather.R
import com.lab.weather.databinding.FragmentHomeBinding
import com.lab.weather.main.MainActivity
import com.lab.weather.main.ui.Status
import com.lab.weather.main.ui.WeatherViewModel
import com.lab.weather.models.Weather
import com.lab.weather.shared.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.errorText
        //homeViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

        // setup observers
        observeWeather()
        observeFavCity()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        weatherViewModel.favCityLiveData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.background = resources.getDrawable(R.drawable.sunny_weather_background)
    }

    private fun observeWeather() {
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> Log.d("WeatherLiveData", "Loading")
                Status.SUCCESS -> onWeather(it.data)
                Status.ERROR -> onError(it.error)
            }
        })
    }

    private fun observeFavCity() {
        weatherViewModel.favCityLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null)
                weatherViewModel.getWeather(it)
            else {
                onError(getString(R.string.no_featured_cities))
            }
        })
    }

    private fun onWeather(weather: Weather?) {
        if (weather == null) {
            onError(getString(R.string.no_weather_data))
            return
        }

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

    private fun onError(msg: String?) {
        binding.errorText.text = msg

        setErrorVisible(true)
        setWeatherVisible(false)
    }

    private fun setErrorVisible(value: Boolean) {
        if (value) {
            binding.errorText.visibility = View.VISIBLE
        } else {
            binding.errorText.visibility = View.GONE
        }
    }
    private fun setWeatherVisible(value: Boolean) {
        if (value) {
            binding.weather.visibility = View.VISIBLE
        } else {
            binding.weather.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        val activity = requireActivity() as MainActivity
        activity.setRefreshBtnVisible(true)
        activity.setFavouriteBtnVisible(false)
    }
}