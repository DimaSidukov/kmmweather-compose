package ui

import repositories.WeatherRepository

class SearchViewModel(private val repository: WeatherRepository) {

    suspend fun getCoordinates(address: String) = repository.getCoordinates(address)

}