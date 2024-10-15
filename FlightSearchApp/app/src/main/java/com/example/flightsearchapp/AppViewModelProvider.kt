package com.example.flightsearchapp

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.ui.screens.FavoriteViewModel
import com.example.flightsearchapp.ui.screens.FlightViewModel
import com.example.flightsearchapp.ui.screens.UserSearchViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FlightViewModel(
                flightSearchApplication().container.airportRepository,
                flightSearchApplication().container.favoriteRepository
            )
        }

        initializer {
            FavoriteViewModel(
                flightSearchApplication().container.favoriteRepository,
                flightSearchApplication().container.airportRepository
            )
        }

        initializer {
            UserSearchViewModel(
                flightSearchApplication().usersSearchesRepository,
                flightSearchApplication().container.airportRepository
            )
        }
    }
}

fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)