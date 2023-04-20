package com.example.domain.entities

interface Forecast {

    val latitude: Double

    val longitude: Double

    var address: String

    val dateTemperatureRange: String

    val currentHourTemperature: Int

    val weatherDescription: String

    val dayTemperatureString: String

    val dayTemperatureList: List<Int>
}