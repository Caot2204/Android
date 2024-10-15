package com.example.flightsearchapp.ui.screens.flight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.ui.theme.Shapes

@Composable
fun FlightsList(
    flights: List<Flight>,
    label: String,
    onUpdateFlights: () -> Unit,
    onSaveFavorite: (String, String) -> Unit,
    onDeleteFavorite: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    LocalSoftwareKeyboardController.current?.hide()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
        modifier = modifier
            .padding(
                start = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            )
    ) {
        item {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )
        }
        items(flights) { flight ->
            FlightItem(
                flight = flight,
                onUpdateFlights = onUpdateFlights,
                onFavoriteClick = {
                    if (flight.isFavorite) {
                        onDeleteFavorite(
                            flight.iataCodeDepart,
                            flight.iataCodeArrive
                        )
                    } else {
                        onSaveFavorite(
                            flight.iataCodeDepart,
                            flight.iataCodeArrive
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun FlightItem(
    flight: Flight,
    onUpdateFlights: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = Shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.depart),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                )
                AirportLabel(
                    airportCode = flight.iataCodeDepart,
                    airportName = flight.nameDepart
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                Text(
                    text = stringResource(R.string.arrive),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                )
                AirportLabel(
                    airportCode = flight.iataCodeArrive,
                    airportName = flight.nameArrive
                )
            }
            IconButton(
                onClick = {
                    onFavoriteClick()
                    onUpdateFlights()
                }
            ) {
                Icon(
                    imageVector =
                    if (flight.isFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,

                    contentDescription =
                    if (flight.isFavorite) stringResource(R.string.isFavorite)
                    else stringResource(R.string.notFavorite)
                )
            }
        }
    }
}

@Composable
fun AirportLabel(
    airportCode: String,
    airportName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {  }
) {
    Row(
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Text(
            text = airportCode,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.suggest_space)))
        Text(
            text = airportName,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class FlightForUi (
    val iataCodeDepart: String,
    val nameDepart: String,
    val iataCodeArrive: String,
    val nameArrive: String,
    var isFavorite: Boolean
)

fun Flight.toFlightForUi(): FlightForUi = FlightForUi(
    iataCodeDepart = iataCodeDepart,
    iataCodeArrive = iataCodeArrive,
    nameDepart = nameDepart,
    nameArrive = nameArrive,
    isFavorite = isFavorite
)