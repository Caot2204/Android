package com.example.paseaporlostuxtlas.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasContentType
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasScreen
import com.example.paseaporlostuxtlas.ui.theme.PaseaPorLosTuxtlasTheme
import com.example.paseaporlostuxtlas.ui.viewmodel.LosTuxtlasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LosTuxtlasAppBar(
    canNavigateBack: Boolean,
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun LosTuxtlasApp(
    windowWidthSize: WindowWidthSizeClass,
    windowHeightSize: WindowHeightSizeClass,
    navController: NavHostController = rememberNavController()
) {
    //Jetpack navigation
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LosTuxtlasScreen.valueOf(
        backStackEntry?.destination?.route ?: LosTuxtlasScreen.Start.name
    )

    //ViewModel variables
    val viewModel: LosTuxtlasViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value

    //Adaptive layout
    val contentType = when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> {
            LosTuxtlasContentType.CONTENT_VERTICAL
        }
        WindowWidthSizeClass.Medium -> {
            LosTuxtlasContentType.CONTENT_HORIZONTAL
        }
        WindowWidthSizeClass.Expanded -> {
            LosTuxtlasContentType.CONTENT_EXPANDED
        }
        else -> {
            LosTuxtlasContentType.CONTENT_VERTICAL
        }
    }

    viewModel.setCurrentScreenOrientation(contentType, windowHeightSize)

    Scaffold (
        topBar = {
            LosTuxtlasAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                title = stringResource(currentScreen.title),
                onBackPressed = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LosTuxtlasScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(route = LosTuxtlasScreen.Start.name) {
                StartScreen(
                    uiState = uiState,
                    onCardPressed = { place: Place ->
                        viewModel.updatePlaceSelected(place)
                        if (uiState.isOkForNavigateDetails){
                            navController.navigate(LosTuxtlasScreen.PlaceDetails.name)
                        }
                    },
                    onCategoryClicked = { category ->
                        viewModel.updateCurrentCategoryScreen(category.screen)
                        if(uiState.isOkForNavigateGridPlaces) {
                            navController.navigate(LosTuxtlasScreen.Places.name)
                        }
                    },
                    contentType = contentType,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            composable(route = LosTuxtlasScreen.Places.name) {
                PlaceGrid(
                    places = uiState.placesToShow,
                    onCardPressed = { place ->
                        viewModel.updatePlaceSelected(place)
                        if (uiState.isOkForNavigateDetails){
                            navController.navigate(LosTuxtlasScreen.PlaceDetails.name)
                        }
                    },
                    contentType = contentType,
                    paddingValues = innerPadding,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
            composable(route = LosTuxtlasScreen.PlaceDetails.name) {
                val context = LocalContext.current
                DetailsScreen(
                    place = uiState.placeSelected,
                    onShareButtonClicked = {
                        viewModel.sharePlace(context)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompactPreview() {
    PaseaPorLosTuxtlasTheme {
        LosTuxtlasApp(
            windowWidthSize = WindowWidthSizeClass.Compact,
            windowHeightSize = WindowHeightSizeClass.Compact
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun MediumPreview() {
    PaseaPorLosTuxtlasTheme {
        LosTuxtlasApp(
            windowWidthSize = WindowWidthSizeClass.Medium,
            windowHeightSize = WindowHeightSizeClass.Medium
        )
    }
}

@Preview(showBackground = true, widthDp = 1200)
@Composable
fun ExpandedPreview() {
    PaseaPorLosTuxtlasTheme {
        LosTuxtlasApp(
            windowWidthSize = WindowWidthSizeClass.Expanded,
            windowHeightSize = WindowHeightSizeClass.Expanded
        )
    }
}