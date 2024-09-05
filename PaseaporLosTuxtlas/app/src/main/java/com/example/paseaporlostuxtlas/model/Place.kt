package com.example.paseaporlostuxtlas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.paseaporlostuxtlas.R

enum class PlaceType (@StringRes val title: Int) {
    TURISTICO(title = R.string.turistico),
    RESTAURANTE(title = R.string.restaurante),
    COMIDA_RAPIDA(title = R.string.comida_rapida),
    CAFETERIA(title = R.string.cafeteria),
    ALBERCA(title = R.string.alberca),
    PLAYA(title = R.string.playa),
    RIO(title = R.string.rio),
    IGLESIA(title = R.string.iglesia),
    PARQUE(title = R.string.parque),
    COMIDA_LOCAL(title = R.string.comida_local),
    POZA(title = R.string.poza),
    HOTEL(title = R.string.name_piedra_alta)
}

data class Place(
    @StringRes val name: Int,
    val placeType: PlaceType,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)
