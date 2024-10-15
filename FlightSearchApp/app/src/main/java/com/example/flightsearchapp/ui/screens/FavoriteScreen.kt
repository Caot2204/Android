package com.example.flightsearchapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsearchapp.R
import com.example.flightsearchapp.ui.screens.flight.FlightsList

@Composable
fun FavoriteFlightsScreen(
    favoriteViewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val favoriteFlightsUiState = favoriteViewModel.favoriteFlightsUiState.collectAsState()
    val favoriteFlights = favoriteFlightsUiState.value.favoriteFlights

    if (favoriteFlights.isNotEmpty()) {
        FlightsList(
            flights = favoriteFlights,
            label = stringResource(R.string.favorite),
            onUpdateFlights = {  },
            onSaveFavorite = favoriteViewModel::saveFavoriteFlight,
            onDeleteFavorite = favoriteViewModel::deleteFavoriteFlight,
            modifier = modifier
        )
    } else {
        NoFavoriteScreen(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun NoFavoriteScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = stringResource(R.string.no_favorite)
        )
        Text(
            text = stringResource(R.string.no_favorite),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}