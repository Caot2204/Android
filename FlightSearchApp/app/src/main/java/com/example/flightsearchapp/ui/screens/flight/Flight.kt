package com.example.flightsearchapp.ui.screens.flight

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class Flight(
    val iataCodeDepart: String,
    val nameDepart: String,
    val iataCodeArrive: String,
    val nameArrive: String,
    val isFavorite: Boolean = false
)