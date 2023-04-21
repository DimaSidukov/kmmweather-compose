package ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import repositories.WeatherRepository
import com.example.domain.entities.Forecast as UiForecast

class HomeViewModel(
    private val repository: WeatherRepository
) {

    var state = MutableStateFlow<HomeViewState>(HomeViewState.NoData)
        private set

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun requestData(
        latitude: Double,
        longitude: Double,
        forceRemote: Boolean,
    ) {
        viewModelScope.launch {
            val forecast = repository.getForecastForToday(latitude, longitude, forceRemote)
            forecast.collect {
                state.emit(
                    if (it.data == null) HomeViewState.Error(it.error!!.cause)
                    else HomeViewState.Forecast(it.data!!)
                )
            }
        }
    }

    fun requestLocationList() = viewModelScope.launch {
        repository.getForecastList().let {
            state.emit(HomeViewState.ForecastList(it))
        }
    }

}


interface ViewState

sealed class HomeViewState : ViewState {

    object NoData : HomeViewState()

    class Error(val cause: String) : HomeViewState()

    class Forecast(val forecast: UiForecast) : HomeViewState()

    class ForecastList(val forecastList: List<UiForecast>) : HomeViewState()

}