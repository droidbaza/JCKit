package com.github.droidbaza.jckit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import com.github.droidbaza.jckit.ui.component.LocalButtonColor
import com.github.droidbaza.jckit.ui.component.LocalTextFieldColor
import com.github.droidbaza.jckit.ui.component.rememberButtonColor
import com.github.droidbaza.jckit.ui.component.rememberTextFieldColor

@Immutable
data class JCKitTheme(
    val color: ColorScheme,
    val typography: TypographyScheme = DefaultTypography,
    val size: SizeScheme = DefaultSizing,
    val radius: RadiusScheme = DefaultRadius,
    val border: BorderScheme = DefaultBorder,
    val padding: PaddingScheme = DefaultPadding,
)

val LightTheme = JCKitTheme(LightMode)
val DarkTheme = JCKitTheme(LightMode)

val LocalTheme = compositionLocalOf { LightTheme }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = if (darkTheme) DarkTheme else LightTheme
    val buttonColor = rememberButtonColor()
    val textFieldColor = rememberTextFieldColor()

    CompositionLocalProvider(
        LocalColor provides theme.color,
        LocalButtonColor provides buttonColor,
        LocalTextFieldColor provides textFieldColor,
        LocalTypography provides theme.typography,
        LocalSize provides theme.size,
        LocalRadius provides theme.radius,
        LocalBorder provides theme.border,
        LocalPadding provides theme.padding,
    ) {
        content()
    }
}
