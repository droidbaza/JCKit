package com.github.droidbaza.jckit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PaddingScheme(
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp
)

val DefaultPadding = PaddingScheme(
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
    extraLarge = 24.dp
)

val LocalPadding = staticCompositionLocalOf { DefaultPadding }
