package com.lab.weather.main.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lab.weather.R
import com.lab.weather.databinding.CityItemBinding
import com.lab.weather.models.Geocoding

class CitiesAdapter(
    private var cityList: List<String>,
    private var onClickListener: (String) -> Unit,
    private var onDeleteBtnClickListener: (String) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CityItemBinding.bind(view)

        fun fillFields(city: String, onClickListener: (String) -> Unit) {
            binding.cityName.text = city

            binding.deleteButton.setOnClickListener {
                onDeleteBtnClickListener(city)
            }

            itemView.setOnClickListener { _ ->
                onClickListener(city)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CityViewHolder(layoutInflater.inflate(R.layout.city_item, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.fillFields(city, onClickListener)
    }

    override fun getItemCount(): Int = cityList.size
}