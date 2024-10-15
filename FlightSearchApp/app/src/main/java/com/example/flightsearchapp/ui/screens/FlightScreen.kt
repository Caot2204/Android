package com.example.flightsearchapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.AppViewModelProvider
import com.example.flightsearchapp.R
import com.example.flightsearchapp.ui.screens.flight.FlightsList
import com.example.flightsearchapp.ui.theme.Shapes

@Composable
fun FlightsApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            FlightTopAppBar(title = stringResource(R.string.app_name))
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        FlightSearchScreen(
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier
    )
}

@Composable
fun FlightSearchScreen(
    modifier: Modifier = Modifier,
    flightViewModel: FlightViewModel = viewModel(factory = AppViewModelProvider.Factory),
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    userSearchViewModel: UserSearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val flightsUiState = flightViewModel.flightsUiState.collectAsState()
    val flights = flightsUiState.value.flights

    val focusRequester = remember {
        FocusRequester()
    }

    Column(modifier = modifier) {
        FlightSearchTextField(
            usersSearch = userSearchViewModel.currentSearch,
            onUsersSearchChange = {
                userSearchViewModel.updateUserInput(it)
            },
            modifier = Modifier.focusRequester(focusRequester)
        )
        if (userSearchViewModel.currentSearch == "") {
            FavoriteFlightsScreen(
                favoriteViewModel = favoriteViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (userSearchViewModel.isUserSearching) {
            UsersSearchesScreen(
                onSuggestSelected = flightViewModel::updateUserSelection,
                userSearchViewModel = userSearchViewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small)
                    )
            )
        } else {
            if (flights.isNotEmpty()) {
                FlightsList(
                    flights = flights,
                    label = stringResource(R.string.flights_to, flightViewModel.currentIataCode),
                    onUpdateFlights = flightViewModel::updateFlightsAfterFavoriteClicked,
                    onSaveFavorite = favoriteViewModel::saveFavoriteFlight,
                    onDeleteFavorite = favoriteViewModel::deleteFavoriteFlight,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FlightSearchTextField(
    usersSearch: String,
    onUsersSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = usersSearch,
        onValueChange = onUsersSearchChange,
        shape = Shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        label = { Text(text = stringResource(R.string.search_airport)) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    )

}