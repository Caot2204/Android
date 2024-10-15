package com.example.flightsearchapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.data.repositories.AirportRepository
import com.example.flightsearchapp.data.repositories.FavoriteRepository
import com.example.flightsearchapp.data.repositories.UsersSearchesRepository
import com.example.flightsearchapp.ui.screens.flight.Flight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlightViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    var currentIataCode: String by mutableStateOf("")
        private set

    private var currentAirportName: String by mutableStateOf("")

    private val _flightsUiState = MutableStateFlow(FlightsUiState())
    val flightsUiState: StateFlow<FlightsUiState> = _flightsUiState

    fun updateUserSelection(iataDepart: String, nameDepart: String) {
        currentIataCode = iataDepart
        currentAirportName = nameDepart
        generateFlightsFromUserSelection()
    }

    fun updateFlightsAfterFavoriteClicked() {
        generateFlightsFromUserSelection()
    }

    private fun generateFlightsFromUserSelection() {
        viewModelScope.launch {
            val airportsDestination = airportRepository.getAirportsExcepts(currentIataCode).first()
            val currentFlights: MutableList<Flight> = mutableListOf()
            for (airport in airportsDestination) {
                currentFlights.add(
                    Flight(
                        iataCodeDepart = currentIataCode,
                        nameDepart = currentAirportName,
                        iataCodeArrive = airport.iataCode,
                        nameArrive = airport.name,
                        isFavorite = favoriteRepository.isFavorite(currentIataCode, airport.iataCode)
                    )
                )
            }
            _flightsUiState.emit(FlightsUiState(currentFlights))
        }
    }

}

data class FlightsUiState(
    val flights: List<Flight> = listOf()
)
