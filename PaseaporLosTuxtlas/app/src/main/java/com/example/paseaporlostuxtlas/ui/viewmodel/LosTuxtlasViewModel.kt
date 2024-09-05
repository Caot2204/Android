package com.example.paseaporlostuxtlas.ui.viewmodel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.lifecycle.ViewModel
import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.data.LocalDataPlaceProvider
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasContentType
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasScreen
import com.example.paseaporlostuxtlas.ui.util.isOkForNavigateDetails
import com.example.paseaporlostuxtlas.ui.util.isOkForNavigateGridPlaces
import com.example.paseaporlostuxtlas.ui.util.validateLandscapeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.core.content.ContextCompat

class LosTuxtlasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LosTuxtlasUiState())
    val uiState: StateFlow<LosTuxtlasUiState> = _uiState

    fun setCurrentScreenOrientation(contentType: LosTuxtlasContentType, heightSize: WindowHeightSizeClass) {
        _uiState.update {
            it.copy(
                isLanscapeMode = validateLandscapeMode(contentType, heightSize)
            )
        }
        setOkForNavigateDetails(contentType, heightSize)
        setOkForNavigateGridPlaces(contentType, heightSize)
    }

    private fun setOkForNavigateDetails(contentType: LosTuxtlasContentType, heightSize: WindowHeightSizeClass) {
        _uiState.update {
            it.copy(
                isOkForNavigateDetails = isOkForNavigateDetails(contentType, heightSize)
            )
        }
    }

    private fun setOkForNavigateGridPlaces(contentType: LosTuxtlasContentType, heightSize: WindowHeightSizeClass) {
        _uiState.update {
            it.copy(
                isOkForNavigateGridPlaces = isOkForNavigateGridPlaces(contentType, heightSize)
            )
        }
    }

    fun updatePlaceSelected(place: Place) {
        _uiState.update { currentState ->
            currentState.copy(
                placeSelected = place
            )
        }
    }

    fun updateCurrentCategoryScreen(currentCategoryScreen: LosTuxtlasScreen) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategoryScreen = currentCategoryScreen,
                placesToShow = when (currentCategoryScreen) {
                    LosTuxtlasScreen.Familiar -> LocalDataPlaceProvider.allFamiliarPlaces
                    LosTuxtlasScreen.Comida -> LocalDataPlaceProvider.allFoodShops
                    LosTuxtlasScreen.Acuatico -> LocalDataPlaceProvider.allAcuaticsPlaces
                    LosTuxtlasScreen.Hospedaje -> LocalDataPlaceProvider.allHotels
                    else -> emptyList()
                }
            )
        }
    }

    fun sharePlace(intentContext: Context) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(
                    R.string.message_share,
                    intentContext.getString(uiState.value.placeSelected.name)
                )
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            ContextCompat.startActivity(intentContext, shareIntent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_avaible),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}