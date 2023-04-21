package di

import com.example.data.api.WeatherApi
import com.example.data.local.AppSettings
import com.example.data.local.LocalWeatherSource
import com.example.data.local.buildDatabaseDriveFactory
import com.example.data.remote.RemoteWeatherSource
import repositories.WeatherRepository

class InjectionHelper {

    companion object {
        private val weatherApi = WeatherApi(
            buildHttpClient()
        )

        private val remoteWeatherSourceModule = RemoteWeatherSource(
            weatherApi
        )

        private val localWeatherSourceModule = LocalWeatherSource(
            buildDatabaseDriveFactory()
        )

        private val settingsModule = AppSettings()

        val weatherRepository = WeatherRepository(
            remoteWeatherSourceModule,
            localWeatherSourceModule
        )

    }


}