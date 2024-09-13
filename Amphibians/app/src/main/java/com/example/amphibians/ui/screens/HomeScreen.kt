package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian
import com.example.amphibians.ui.AmphibianUiState
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is AmphibianUiState.Success ->
            ListScreen(
                amphibians = amphibianUiState.amphibians,
                contentPadding = contentPadding,
                modifier = modifier
        )
        is AmphibianUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(R.dimen.loading_img_size)),
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_fail))
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun ListScreen(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.column_spacing)),
        contentPadding = contentPadding,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.column_padding)
            )
    ) {
        items(amphibians) { amphibian ->
            AmphibianItem(amphibian = amphibian)
        }
    }
}

@Composable
fun AmphibianItem(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
    ) {
        Column {
            Text(
                text = stringResource(
                    R.string.amphibian_label,
                    amphibian.name,
                    amphibian.type
                ),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.label_padding))
                    .fillMaxWidth()
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_loading),
                contentDescription = amphibian.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.image_item_max_heigh))
            )
            Text(
                text = amphibian.description,
                modifier = Modifier.padding(dimensionResource(R.dimen.text_padding))
            )
        }
    }
}

@Preview
@Composable
fun AmphibianItemPreview() {
    AmphibiansTheme {
        val amphibian = Amphibian(
            name = "Rana rene",
            type = "Ranita",
            imgSrc = "",
            description = "Es una rana muy bonita que canta canciones y sale en television"
        )
        AmphibianItem(amphibian = amphibian)
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    val amphibians = List(10) {
        Amphibian(
            name = "Rana rene",
            type = "Ranita",
            imgSrc = "",
            description = "Es una rana muy bonita que canta canciones y sale en television" )
    }
    ListScreen(amphibians = amphibians)
}