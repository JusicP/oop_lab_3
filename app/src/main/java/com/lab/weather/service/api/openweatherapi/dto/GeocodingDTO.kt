package com.lab.weather.service.api.openweatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Geocoding

data class GeocodingDtoOpenWeatherMap (
    @SerializedName("name") var name: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("country") var country: String? = null
    // TODO: what about local names?
)

fun GeocodingDtoOpenWeatherMap.asDomainModel(): Geocoding {
    return Geocoding(
        city = name,
        state = state,
        country = country
    )
}
