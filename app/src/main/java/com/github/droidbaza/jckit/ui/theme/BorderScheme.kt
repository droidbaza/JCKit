package com.github.droidbaza.jckit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BorderScheme(
    val thin: Dp,
    val medium: Dp,
    val thick: Dp
)

val DefaultBorder = BorderScheme(
    thin = 1.dp,
    medium = 2.dp,
    thick = 4.dp
)

val LocalBorder = staticCompositionLocalOf { DefaultBorder }