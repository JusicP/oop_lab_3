package com.lab.weather.main.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.lab.weather.R
import com.lab.weather.databinding.FragmentSearchBinding
import com.lab.weather.main.MainActivity
import com.lab.weather.main.ui.Status
import com.lab.weather.main.ui.WeatherViewModel
import com.lab.weather.models.Geocoding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                weatherViewModel.getGeocoding(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        observeGeoData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeGeoData() {
        weatherViewModel.geoLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onGeoData(it.data)
                Status.ERROR -> onError(it.error)
            }
        })
    }

    private fun onGeoData(geoData: List<Geocoding>?) {
        setLoadingVisible(false)
        setErrorVisible(false)
        setRecyclerVisible(true)

        if (geoData == null) {
            onError(getString(R.string.no_cities_found))
            return
        }

        binding.recyclerSearch.adapter = SearchAdapter(
            geoData,
            { geocoding ->
                onItemSelected(geocoding)
            },
            { geocoding ->
                onAddCityClick(geocoding)
            }
        )

        binding.recyclerSearch.layoutManager = LinearLayoutManager(context)
    }

    private fun onLoading() {
        setLoadingVisible(true)
        setErrorVisible(false)
        setRecyclerVisible(false)
    }

    private fun onError(msg: String?) {
        setLoadingVisible(false)
        setErrorVisible(true, msg)
        setRecyclerVisible(false)
    }


    private fun setLoadingVisible(state: Boolean) {
        if (state) {
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.visibility = View.GONE
        }
    }

    private fun setRecyclerVisible(state: Boolean) {
        if (state) {
            binding.recyclerSearch.visibility = View.VISIBLE
        } else {
            binding.recyclerSearch.visibility = View.GONE
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

    private fun onItemSelected(geocoding: Geocoding) {
        val bundle = Bundle()
        bundle.putString("city", geocoding.city)

        Navigation.findNavController(binding.root).navigate(R.id.nav_ctiy_preview, bundle)
    }

    private fun onAddCityClick(geocoding: Geocoding) {
        if (geocoding.city != null)
            weatherViewModel.addFeaturedCity(geocoding.city!!)
    }

    override fun onResume() {
        super.onResume()

        val activity = requireActivity() as MainActivity
        activity.setRefreshBtnVisible(false)
        activity.setFavouriteBtnVisible(false)
    }
}