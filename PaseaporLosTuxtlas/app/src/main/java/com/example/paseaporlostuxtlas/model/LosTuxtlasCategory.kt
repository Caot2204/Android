package com.example.paseaporlostuxtlas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasScreen

data class LosTuxtlasCategory(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    val screen: LosTuxtlasScreen
)
