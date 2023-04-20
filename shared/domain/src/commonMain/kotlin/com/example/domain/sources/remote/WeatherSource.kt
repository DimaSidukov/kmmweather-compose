package com.example.domain.sources.remote

import com.example.domain.entities.Weather

interface WeatherSource {

    suspend fun getWeatherForToday(latitude: Double, longitude: Double): Weather

}