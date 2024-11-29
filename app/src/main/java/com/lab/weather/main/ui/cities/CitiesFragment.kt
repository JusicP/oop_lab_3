package com.lab.weather.main.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.lab.weather.R
import com.lab.weather.databinding.FragmentCitiesBinding
import com.lab.weather.main.MainActivity
import com.lab.weather.main.ui.WeatherViewModel
import com.lab.weather.main.ui.search.SearchAdapter
import com.lab.weather.models.Geocoding

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.citySearch.setOnClickListener{ view ->
            Navigation.findNavController(view).navigate(R.id.nav_search)
        }

        observeFeaturedCities()

        weatherViewModel.getFeaturedCities()

        return root
    }

    private fun observeFeaturedCities() {
        weatherViewModel.featuredCitiesLiveData.observe(this, Observer {
            onFeaturedCities(it)
        })
    }

    private fun onFeaturedCities(cities: Set<String>)
    {
        binding.recyclerCities.adapter = CitiesAdapter(
            cities.toList(),
            { city ->
                onItemSelected(city)
            },
            { city ->
                onDeleteFeaturedCity(city)
            }
        )

        binding.recyclerCities.layoutManager = LinearLayoutManager(context)
    }

    private fun onItemSelected(city: String) {
        val bundle = Bundle()
        bundle.putString("city", city)

        Navigation.findNavController(binding.root).navigate(R.id.nav_ctiy_preview, bundle)
    }

    private fun onDeleteFeaturedCity(city: String) {
        weatherViewModel.deleteFeaturedCity(city)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        val activity = requireActivity() as MainActivity
        activity.setRefreshBtnVisible(false)
        activity.setFavouriteBtnVisible(false)
    }
}