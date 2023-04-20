package com.example.data.body

import com.example.domain.entities.Forecast
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ForecastBody(
    @SerialName("latitude")
    override val latitude: Double,
    @SerialName("longitude")
    override val longitude: Double,
    @SerialName("address")
    override var address: String,
    @SerialName("dateTemperatureRange")
    override val dateTemperatureRange: String,
    @SerialName("currentHourTemperature")
    override val currentHourTemperature: Int,
    @SerialName("weatherDescription")
    override val weatherDescription: String,
    @SerialName("dayTemperatureString")
    override val dayTemperatureString: String,
) : Forecast {
    override val dayTemperatureList: List<Int> =
        if (dayTemperatureString.isEmpty()) listOf()
        else dayTemperatureString.split(",").map { it.toInt() }
}