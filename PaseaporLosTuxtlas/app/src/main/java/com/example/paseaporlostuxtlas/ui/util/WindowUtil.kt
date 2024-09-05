package com.example.paseaporlostuxtlas.ui.util

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import com.example.paseaporlostuxtlas.ui.navigation.LosTuxtlasContentType

/**
 * Verify if a phone is in landscape mode
 */
fun validateLandscapeMode(
    contentType: LosTuxtlasContentType,
    heightSize: WindowHeightSizeClass
) : Boolean {
    return ((contentType == LosTuxtlasContentType.CONTENT_HORIZONTAL ||
            contentType == LosTuxtlasContentType.CONTENT_EXPANDED) &&
            heightSize == WindowHeightSizeClass.Compact)
}

fun isOkForNavigateDetails(
    contentType: LosTuxtlasContentType,
    heightSize: WindowHeightSizeClass
) : Boolean {

    val isLandscapeMode = validateLandscapeMode(contentType, heightSize)

    return isLandscapeMode ||
        contentType == LosTuxtlasContentType.CONTENT_VERTICAL ||
        contentType == LosTuxtlasContentType.CONTENT_HORIZONTAL
}

fun isOkForNavigateGridPlaces(
    contentType: LosTuxtlasContentType,
    heightSize: WindowHeightSizeClass
) : Boolean {

    val isLandscapeMode = validateLandscapeMode(contentType, heightSize)

    return isLandscapeMode || contentType == LosTuxtlasContentType.CONTENT_VERTICAL
}