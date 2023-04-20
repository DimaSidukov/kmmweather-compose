package com.example.domain.entities

import com.example.domain.entities.Hourly
import com.example.domain.entities.HourlyUnits

interface Weather {

    val elevation: Double

    val generationTimeMs: Double

    val hourly: Hourly

    val hourlyUnits: HourlyUnits

    val latitude: Double

    val longitude: Double

    val timezone: String

    val timezoneAbbreviation: String

    val utcOffsetSeconds: Int

}