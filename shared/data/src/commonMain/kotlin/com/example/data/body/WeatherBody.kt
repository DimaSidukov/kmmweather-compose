package com.example.data.body

import com.example.domain.entities.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherBody(
    @SerialName("elevation")
    override val elevation: Double,
    @SerialName("generationtime_ms")
    override val generationTimeMs: Double,
    @SerialName("hourly")
    override val hourly: HourlyBody,
    @SerialName("hourly_units")
    override val hourlyUnits: HourlyUnitsBody,
    @SerialName("latitude")
    override val latitude: Double,
    @SerialName("longitude")
    override val longitude: Double,
    @SerialName("timezone")
    override val timezone: String,
    @SerialName("timezone_abbreviation")
    override val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    override val utcOffsetSeconds: Int
) : Weather