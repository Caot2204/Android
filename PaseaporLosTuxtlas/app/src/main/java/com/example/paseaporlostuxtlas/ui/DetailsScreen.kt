package com.example.paseaporlostuxtlas.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.model.Place

@Composable
fun DetailsScreen(
    place: Place,
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
        modifier = modifier
    ) {
        item {
            PlaceImageAndName(
                place = place,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            PlaceDescriptionAndButton(
                place = place,
                onShareButtonClicked = onShareButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
            )

        }
    }
}

@Composable
fun PlaceImageAndName(
    place: Place,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(place.image),
            contentDescription = stringResource(place.name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = dimensionResource(R.dimen.detail_place_heigth_image))
        )
        Text(
            text = stringResource(place.name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(place.placeType.title),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PlaceDescriptionAndButton(
    place: Place,
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
        modifier = modifier
    ){
        Text(
            text = stringResource(place.description),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_details)))
        Button(
            onClick = onShareButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Filled.Share,
                contentDescription = stringResource(R.string.share)
            )
            Text(text = stringResource(R.string.share))
        }
    }
}