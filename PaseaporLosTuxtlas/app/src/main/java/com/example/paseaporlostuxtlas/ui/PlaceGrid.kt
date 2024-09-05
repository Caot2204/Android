package com.example.paseaporlostuxtlas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasContentType
import com.example.paseaporlostuxtlas.ui.util.PlaceItem

@Composable
fun PlaceGrid(
    places: List<Place>,
    onCardPressed: (Place) -> Unit,
    contentType: LosTuxtlasContentType,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (contentType == LosTuxtlasContentType.CONTENT_HORIZONTAL ||
            contentType == LosTuxtlasContentType.CONTENT_EXPANDED) {
            Text(
                text = stringResource(R.string.places),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        LazyVerticalStaggeredGrid(
            columns = if (contentType == LosTuxtlasContentType.CONTENT_VERTICAL) {
                StaggeredGridCells.Fixed(2)
            } else {
                StaggeredGridCells.Adaptive(dimensionResource(R.dimen.max_weight_card_grid))
            },
            verticalItemSpacing = dimensionResource(R.dimen.space_small),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
            contentPadding = paddingValues,
            content = {
                items (places) { place ->
                    PlaceItem(
                        place = place,
                        onClickItem = { onCardPressed(place) }
                    )
                }
            }
        )
    }
}
