package com.example.flightsearchapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.data.repositories.AirportRepository
import com.example.flightsearchapp.data.repositories.UsersSearchesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserSearchViewModel(
    private val usersSearchesRepository: UsersSearchesRepository,
    private val airportRepository: AirportRepository
) : ViewModel() {

    var currentSearch: String by mutableStateOf("")
        private set

    var isUserSearching: Boolean by mutableStateOf(false)
        private set

    private val _airportsSearchResultUiState = MutableStateFlow(AirportsSearchResultUiState())
    val airportsSearchResultUiState: StateFlow<AirportsSearchResultUiState> = _airportsSearchResultUiState

    val userSearchesUiState: StateFlow<UserSearchesUiState> =
        usersSearchesRepository.usersSearch.map { UserSearchesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = UserSearchesUiState()
            )

    fun updateUserInput(userInput: String) {
        currentSearch = userInput
        isUserSearching = true
        viewModelScope.launch {
            if (currentSearch == "") {
                _airportsSearchResultUiState.emit(AirportsSearchResultUiState())
            } else {
                airportRepository.getAirportsFor(currentSearch).collect { airportsFor ->
                    _airportsSearchResultUiState.emit(AirportsSearchResultUiState(airportsFor))
                }
            }
        }
    }

    fun saveCurrentSearch() {
        isUserSearching = false
        viewModelScope.launch {
            usersSearchesRepository.saveUserSearches(currentSearch)
        }
    }
}

data class AirportsSearchResultUiState(
    val searchResults: List<Airport> = listOf()
)

data class UserSearchesUiState(
    val usersSearches: List<String> = emptyList()
)