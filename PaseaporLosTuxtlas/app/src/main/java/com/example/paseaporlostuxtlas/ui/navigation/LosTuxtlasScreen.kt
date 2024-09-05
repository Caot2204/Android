package com.example.paseaporlostuxtlas.ui.navigation

import androidx.annotation.StringRes
import com.example.paseaporlostuxtlas.R

enum class LosTuxtlasScreen (@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Places(title = R.string.places),
    PlaceDetails(title = R.string.place_details),
    Comida(title = R.string.comida),
    Familiar(title = R.string.familiar),
    Acuatico(title = R.string.acuatico),
    Hospedaje(title = R.string.hospedaje)
}