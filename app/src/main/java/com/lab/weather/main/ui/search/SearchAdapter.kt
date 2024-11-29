package com.lab.weather.main.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lab.weather.R
import com.lab.weather.models.Geocoding
import com.lab.weather.databinding.GeocodingItemBinding

class SearchAdapter(
    private var geocodingList: List<Geocoding>,
    private var onClickListener: (Geocoding) -> Unit,
    private var onAddBtnClickListener: (Geocoding) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = GeocodingItemBinding.bind(view)

        fun fillFields(geocoding: Geocoding, onClickListener: (Geocoding) -> Unit, onAddBtnClickListener: (Geocoding) -> Unit) {
            binding.cityName.text = geocoding.city
            binding.countryName.text = geocoding.country

            binding.addButton.setOnClickListener {
                onAddBtnClickListener(geocoding)
            }

            itemView.setOnClickListener {
                onClickListener(geocoding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(layoutInflater.inflate(R.layout.geocoding_item, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val geocoding = geocodingList[position]
        holder.fillFields(geocoding, onClickListener, onAddBtnClickListener)
    }

    override fun getItemCount(): Int = geocodingList.size
}