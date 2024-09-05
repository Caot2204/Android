package com.example.paseaporlostuxtlas.data

import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.model.LosTuxtlasCategory
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasScreen

object LocalDataCategoryProvider {
    val allCategories = listOf(
        LosTuxtlasCategory(
            title = R.string.familiar,
            image = R.drawable.familiar,
            screen = LosTuxtlasScreen.Familiar
        ),
        LosTuxtlasCategory(
            title = R.string.acuatico,
            image = R.drawable.acuatico,
            screen = LosTuxtlasScreen.Acuatico
        ),
        LosTuxtlasCategory(
            title = R.string.comida,
            image = R.drawable.restaurant,
            screen = LosTuxtlasScreen.Comida
        ),
        LosTuxtlasCategory(
            title = R.string.hospedaje,
            image = R.drawable.hotel,
            screen = LosTuxtlasScreen.Hospedaje
        )
    )
}