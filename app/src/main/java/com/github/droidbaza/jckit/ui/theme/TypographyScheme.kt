package com.github.droidbaza.jckit.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class TypographyScheme(
    val h1: TextStyle,  // Стиль для заголовков 1 уровня
    val h2: TextStyle,  // Стиль для заголовков 2 уровня
    val body: TextStyle,  // Стиль для основного текста
    val button: TextStyle,  // Стиль для текста кнопок
    val caption: TextStyle, // Стиль для подписи или мелкого текста
    val input: TextStyle,
)

val DefaultTypography = TypographyScheme(
    h1 = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    h2 = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
    body = TextStyle(fontSize = 16.sp),
    button = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
    caption = TextStyle(fontSize = 12.sp),
    input = TextStyle(fontSize = 16.sp),
)

@Composable
internal fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}



val LocalTypography = staticCompositionLocalOf { DefaultTypography }

