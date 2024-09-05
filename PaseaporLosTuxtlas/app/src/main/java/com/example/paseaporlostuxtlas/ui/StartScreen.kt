package com.example.paseaporlostuxtlas.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.data.LocalDataCategoryProvider
import com.example.paseaporlostuxtlas.model.LosTuxtlasCategory
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasContentType
import com.example.paseaporlostuxtlas.ui.util.PlaceItem
import com.example.paseaporlostuxtlas.ui.viewmodel.LosTuxtlasUiState

@Composable
fun StartScreen(
    uiState: LosTuxtlasUiState,
    onCardPressed: (Place) -> Unit,
    onCategoryClicked: (LosTuxtlasCategory) -> Unit,
    contentType: LosTuxtlasContentType,
    modifier: Modifier = Modifier
) {
    Log.d("START_SCREEN", contentType.name)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        PrincipalMenu(
            suggestedPlace = uiState.aleatoryPlace,
            onCardPressed = onCardPressed,
            onCategoryClicked = onCategoryClicked,
            isLandscapeMode = uiState.isLanscapeMode,
            modifier = Modifier.weight(1f)
        )
        if (!uiState.isLanscapeMode && contentType != LosTuxtlasContentType.CONTENT_VERTICAL) {
            PlaceGrid(
                places = uiState.placesToShow,
                onCardPressed = onCardPressed,
                contentType = contentType,
                paddingValues = PaddingValues(dimensionResource(R.dimen.padding_small)),
                modifier = Modifier.weight(1f)
            )

        }
        if(!uiState.isLanscapeMode && contentType == LosTuxtlasContentType.CONTENT_EXPANDED) {
            DetailsScreen(
                place = uiState.placeSelected,
                onShareButtonClicked = { /*TODO*/ },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_expanded))
                    .fillMaxWidth()
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
fun PrincipalMenu(
    suggestedPlace: Place,
    onCardPressed: (Place) -> Unit,
    onCategoryClicked: (LosTuxtlasCategory) -> Unit,
    isLandscapeMode: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLandscapeMode) {
        Row(modifier = modifier) {
            SuggestedPlace(
                place = suggestedPlace,
                onCardPressed = onCardPressed,
                modifier = Modifier.weight(1f)
            )
            CategoryList(
                categories = LocalDataCategoryProvider.allCategories,
                onCategoryClicked = onCategoryClicked,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    } else {
        Column(modifier = modifier) {
            SuggestedPlace(
                place = suggestedPlace,
                onCardPressed = onCardPressed,
            )
            CategoryList(
                categories = LocalDataCategoryProvider.allCategories,
                onCategoryClicked = onCategoryClicked,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun SuggestedPlace(
    place: Place,
    onCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.sugerencia_lugar),
            style = MaterialTheme.typography.headlineLarge
        )
        Box (modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            PlaceItem(
                place = place,
                onClickItem = { onCardPressed(place) }
            )
        }
    }
}

@Composable
fun CategoryList(
    categories: List<LosTuxtlasCategory>,
    onCategoryClicked: (LosTuxtlasCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.categorias),
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small))
        ){
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onClickItem = { onCategoryClicked(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: LosTuxtlasCategory,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        onClick = onClickItem,
        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(category.image),
                contentDescription = stringResource(category.title),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(
                    dimensionResource(R.dimen.category_size)
                )
            )
            Text(
                text = stringResource(category.title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}