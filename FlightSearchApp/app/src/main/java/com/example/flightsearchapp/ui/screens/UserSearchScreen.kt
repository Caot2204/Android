package com.example.flightsearchapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.AppViewModelProvider
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.ui.screens.flight.AirportLabel

@Composable
fun UsersSearchesScreen(
    onSuggestSelected: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    userSearchViewModel: UserSearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val airports = userSearchViewModel.airportsSearchResultUiState.collectAsState().value.searchResults
    val usersSearchesUIState = userSearchViewModel.userSearchesUiState.collectAsState()
    val usersSearches = usersSearchesUIState.value.usersSearches

    Column(modifier = modifier) {
        AirportSuggestList(
            airports = airports,
            onSuggestSelected = onSuggestSelected,
            onUpdateUserSearching = userSearchViewModel::saveCurrentSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        )
        UsersSearchesList(
            usersSearches = usersSearches,
            onPreviousSearchSelected = userSearchViewModel::updateUserInput,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AirportSuggestList(
    airports: List<Airport>,
    onSuggestSelected: (String, String) -> Unit,
    onUpdateUserSearching: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
        modifier = modifier
    ) {
        items(airports) { airport ->
            AirportLabel(
                airportCode = airport.iataCode,
                airportName = airport.name,
                onClick = {
                    onUpdateUserSearching()
                    onSuggestSelected(
                        airport.iataCode,
                        airport.name
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun UsersSearchesList(
    usersSearches: List<String>,
    onPreviousSearchSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        item {
            Text(
                text = stringResource(R.string.users_search_label),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )
        }
        items(usersSearches) {
            Text(
                text = it,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onPreviousSearchSelected(it)
                    }
            )
        }
    }
}