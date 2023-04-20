package com.example.domain.sources.local

import com.example.domain.entities.Forecast

interface Database {
    suspend fun insertForecast(forecast: Forecast)
    suspend fun getForecast(latitude: Double, longitude: Double) : Forecast?
    suspend fun getForecastList(): List<Forecast>
}