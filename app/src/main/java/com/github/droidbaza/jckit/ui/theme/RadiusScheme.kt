package com.github.droidbaza.jckit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class RadiusScheme(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val full: Dp
)

val DefaultRadius = RadiusScheme(
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 12.dp,
    large = 16.dp,
    extraLarge = 24.dp,
    full = 200.dp
)

val LocalRadius = compositionLocalOf { DefaultRadius }


