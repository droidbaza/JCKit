package com.github.droidbaza.jckit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp


inline val Color.active: Color
    get() {
        val fraction = 5f / 100f
        return lerp(this, Color.Black, fraction)
    }

inline val Color.disabled: Color
    get() {
        val alpha = 0.7f
        return this.copy(alpha = alpha)
    }

object AppColor {
    val Titanic = Color(0xFF04153E)
    val Rocky = Color(0xFF3D75E4)
    val Rush = Color(0xFFF6F9FE)
    val Pianist = Color(0xFFA2ABBE)
    val Troy = Color(0xFF80848C)
    val Fargo = Color(0xFFE14761)
    val FargoLight = Color(0xFFFBE9EC)
    val Amelie = Color(0xFFFFFFFF)
    val Joker = Color(0x9A80848C)
    val Alien = Color(0xFF81ABEE)
}

@Immutable
data class ColorScheme(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceSecondary: Color,
    val onSurfaceLight: Color,
    val error: Color,
    val onError: Color,
    val disabled: Color,
    val onDisabled: Color,
    val none: Color = Color.Unspecified
)

val LightMode = ColorScheme(
    primary = AppColor.Rocky,
    primaryVariant = AppColor.Alien,
    secondary =  AppColor.Titanic,
    background =  AppColor.Rush,
    surface = AppColor.Amelie,
    onPrimary =  AppColor.Amelie,
    onSecondary =  AppColor.Pianist,
    onBackground =  AppColor.Rocky,
    onSurface =  AppColor.Titanic,
    onSurfaceSecondary = AppColor.Troy,
    error =  AppColor.Fargo,
    onError =  AppColor.FargoLight,
    disabled =  AppColor.Pianist,
    onDisabled =  AppColor.Titanic,
    onSurfaceLight = AppColor.Joker
)

val LocalColor = compositionLocalOf { LightMode }
val LocalContentColor = compositionLocalOf { Color.White }

