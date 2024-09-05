package com.example.paseaporlostuxtlas.ui.viewmodel

import com.example.paseaporlostuxtlas.data.LocalDataPlaceProvider
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasScreen

data class LosTuxtlasUiState(
    val placesToShow: List<Place> = LocalDataPlaceProvider.allFamiliarPlaces,
    val aleatoryPlace: Place = LocalDataPlaceProvider.getAleatoryPlace(),
    val placeSelected: Place = LocalDataPlaceProvider.allFamiliarPlaces.first(),
    val currentCategoryScreen: LosTuxtlasScreen = LosTuxtlasScreen.Familiar,
    val currentIndexPlaceSelected: Int = 0,
    val isLanscapeMode: Boolean = false,
    val isOkForNavigateDetails: Boolean = true,
    val isOkForNavigateGridPlaces: Boolean = true
)
