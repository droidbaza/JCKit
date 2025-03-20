package com.github.droidbaza.jckit.ui.theme


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SizeScheme(
    val control: Dp,
    val controlLarge: Dp,
    val icon: Dp,
    val iconSmall: Dp,
    val iconLarge: Dp,
    val iconExtraLarge: Dp
)

val DefaultSizing = SizeScheme(
    control = 48.dp,
    controlLarge = 56.dp,
    iconSmall = 12.dp,
    icon = 24.dp,
    iconLarge = 32.dp,
    iconExtraLarge = 48.dp
)
val LocalSize = staticCompositionLocalOf { DefaultSizing }
