package com.lab.weather.service.api.weatherapi.dto

import com.google.gson.annotations.SerializedName
import com.lab.weather.models.Geocoding

data class GeocodingDtoWeatherApi (
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("country") var country: String? = null
)

fun GeocodingDtoWeatherApi.asDomainModel(): Geocoding {
    return Geocoding(
        city = name,
        state = region,
        country = country
    )
}
