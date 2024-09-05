package com.example.paseaporlostuxtlas.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
fun PlaceItem(
    place: Place,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        onClick = onClickItem,
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(place.image),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(place.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = dimensionResource(R.dimen.min_card_height),
                        max = dimensionResource(R.dimen.max_card_height)
                    )
            )
            Column (
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.medium))
            ) {
                Text(
                    text = stringResource(place.name),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                Text(
                    text = stringResource(id = place.placeType.title),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}