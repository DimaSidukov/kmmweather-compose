package com.example.data.local


import com.example.data.body.ForecastBody
import com.example.domain.entities.Forecast
import com.example.domain.sources.local.Database
import com.example.weather.cache.AppDatabase

class LocalWeatherSource(databaseDriverFactory: DatabaseDriverFactory) : Database {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    override suspend fun insertForecast(forecast: Forecast) {
        dbQuery.insertForecast(
            forecast.latitude,
            forecast.longitude,
            forecast.address,
            forecast.dateTemperatureRange,
            forecast.currentHourTemperature,
            forecast.weatherDescription,
            forecast.dayTemperatureString
        )
    }

    override suspend fun getForecast(latitude: Double, longitude: Double): Forecast? {
        return try {
            val forecastQuery = dbQuery.selectForecastByLocation(latitude, longitude).executeAsOne()
            ForecastBody(
                forecastQuery.latitude,
                forecastQuery.longitude,
                forecastQuery.address,
                forecastQuery.dateTemperatureRange,
                forecastQuery.currentHourTemperature,
                forecastQuery.weatherDescription,
                forecastQuery.dayTemperatureString
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getForecastList(): List<Forecast> = dbQuery
        .selectAll()
        .executeAsList()
        .map { forecastQuery ->
            ForecastBody(
                forecastQuery.latitude,
                forecastQuery.longitude,
                forecastQuery.address,
                forecastQuery.dateTemperatureRange,
                forecastQuery.currentHourTemperature,
                forecastQuery.weatherDescription,
                forecastQuery.dayTemperatureString
            )
        }
}