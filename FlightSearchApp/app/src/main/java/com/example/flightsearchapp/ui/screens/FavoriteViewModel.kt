package com.example.flightsearchapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.data.repositories.AirportRepository
import com.example.flightsearchapp.data.repositories.FavoriteRepository
import com.example.flightsearchapp.ui.screens.flight.Flight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val airportRepository: AirportRepository
) : ViewModel() {
    private val favoriteUiState: StateFlow<FavoriteUiState> =
        favoriteRepository.getAllFavorites().map { FavoriteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000L),
                initialValue = FavoriteUiState()
            )

    private val _favoriteFlightsUiState = MutableStateFlow(FavoriteFlightsUiState())
    val favoriteFlightsUiState: StateFlow<FavoriteFlightsUiState> = _favoriteFlightsUiState

    init {
        updateFavoriteFlightsForUi()
    }

    private fun updateFavoriteFlightsForUi() {
        viewModelScope.launch {
            favoriteUiState.collect { favoriteUiState ->
                val favorites = favoriteUiState.favorites
                val flightsForUi: MutableList<Flight> = mutableListOf()
                for (favorite in favorites) {
                    flightsForUi.add(favorite.toFlight())
                }
                _favoriteFlightsUiState.emit(FavoriteFlightsUiState(flightsForUi))
            }
        }
    }

    fun saveFavoriteFlight(iataDepart: String, iataDestination: String) {
        viewModelScope.launch {
            val newFavorite = Favorite(
                departureCode = iataDepart,
                destinationCode = iataDestination
            )
            favoriteRepository.saveFavorite(newFavorite)
        }
    }

    fun deleteFavoriteFlight(iataDepart: String, iataDestination: String) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(
                iataDepart =  iataDepart,
                iataArrive = iataDestination
            )
        }
    }

    private suspend fun Favorite.toFlight(): Flight = Flight(
        iataCodeDepart = departureCode,
        nameDepart = airportRepository.getAirportName(departureCode),
        iataCodeArrive = destinationCode,
        nameArrive = airportRepository.getAirportName(destinationCode),
        isFavorite = favoriteRepository.isFavorite(departureCode, destinationCode)
    )

}

private data class FavoriteUiState(
    val favorites: List<Favorite> = listOf()
)

data class FavoriteFlightsUiState(
    val favoriteFlights: List<Flight> = listOf()
)