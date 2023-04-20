package com.example.data.body

import com.example.domain.entities.HourlyUnits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnitsBody(
    @SerialName("temperature_2m")
    override val temperature: String,
    @SerialName("time")
    override val time: String,
    @SerialName("weathercode")
    override val weatherCode: String
) : HourlyUnits