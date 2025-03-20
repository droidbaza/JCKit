package com.github.droidbaza.jckit.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.droidbaza.jckit.ui.theme.JCKitTheme
import com.github.droidbaza.jckit.ui.theme.LocalBorder
import com.github.droidbaza.jckit.ui.theme.LocalPadding
import com.github.droidbaza.jckit.ui.theme.LocalRadius
import com.github.droidbaza.jckit.ui.theme.LocalSize
import com.github.droidbaza.jckit.ui.theme.LocalTheme
import com.github.droidbaza.jckit.ui.theme.LocalTypography
import com.github.droidbaza.jckit.ui.theme.active
import com.github.droidbaza.jckit.ui.theme.disabled

@Immutable
data class ButtonColorStyle(
    val color: Color = Color.Unspecified,
    val disabledColor: Color = Color.Unspecified,
    val pressedColor: Color = Color.Unspecified,
    val errorColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val pressedContentColor: Color = Color.Unspecified,
    val errorContentColor: Color = Color.Unspecified,
) {

    @Stable
    internal fun getContentColor(
        pressed: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false
    ): Color {
        return when {
            !enabled -> disabledContentColor
            error -> errorContentColor
            pressed -> pressedContentColor
            else -> contentColor
        }
    }

    @Stable
    internal fun getColor(
        pressed: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false
    ): Color {
        return when {
            !enabled -> disabledColor
            error -> errorColor
            pressed -> pressedColor
            else -> color
        }
    }
}

@Immutable
data class ButtonColorStyles(
    val primary: ButtonColorStyle,
    val secondary: ButtonColorStyle,
    val outline: ButtonColorStyle,
)

val LocalButtonColor = compositionLocalOf<ButtonColorStyles> {
    error("No ButtonStyleTokens provided")
}


@Composable
fun     rememberButtonColor(
    theme: JCKitTheme = LocalTheme.current
): ButtonColorStyles {
    return with(theme.color) {
        ButtonColorStyles(
            primary = ButtonColorStyle(
                color = primary,
                disabledColor = primary.disabled,
                pressedColor = primary.active,
                errorColor = error,

                contentColor = onPrimary,
                disabledContentColor = onPrimary.disabled,
                pressedContentColor = onPrimary.active,
                errorContentColor = onError,
            ),
            secondary = ButtonColorStyle(
                color = primaryVariant,
                disabledColor = primaryVariant.disabled,
                pressedColor = primaryVariant.active,
                errorColor = error,

                contentColor = onPrimary,
                disabledContentColor = onPrimary.disabled,
                pressedContentColor = onPrimary.active,
                errorContentColor = onError,
            ),
            outline = ButtonColorStyle(
                contentColor = primary,
                disabledContentColor = primary.disabled,
                pressedContentColor = primary.active,
                errorContentColor = error,
            ),
        )
    }
}


@Composable
fun Button(
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    style: ButtonColorStyle = LocalButtonColor.current.primary,
    loading: Boolean = false,
    error: Boolean = false,
    borderWidth: Dp? = null,
    cornerRadius: Dp = LocalRadius.current.medium,
    height: Dp = LocalSize.current.control,
    padding: Dp = LocalPadding.current.medium,
    interactionSource: MutableInteractionSource? = null,
    contentStart: @Composable (RowScope.() -> Unit)? = null,
    contentEnd: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val color = style.getColor(pressed, enabled, error)
    val contentColor = style.getContentColor(pressed, enabled, error)
    val border = if (borderWidth != null) BorderStroke(borderWidth, contentColor) else null
    val shape =
        if (cornerRadius == LocalRadius.current.full) CircleShape else RoundedCornerShape(
            cornerRadius
        )
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = !loading && enabled,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier
                .height(height)
                .padding(padding),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                Spinner()
            } else {
                contentStart?.invoke(this)
                content(this)
                contentEnd?.invoke(this)
            }
        }
    }
}

@Composable
fun TextButton(
    title: String = "Title",
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    style: ButtonColorStyle = LocalButtonColor.current.primary,
    loading: Boolean = false,
    error: Boolean = false,
    borderWidth: Dp? = null,
    cornerRadius: Dp = LocalRadius.current.medium,
    height: Dp = LocalSize.current.control,
    padding: Dp = LocalPadding.current.medium,
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTypography.current.button,
    subtitleStyle: TextStyle = LocalTypography.current.caption,
    contentStart: @Composable (RowScope.() -> Unit)? = null,
    contentEnd: @Composable (RowScope.() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = cornerRadius,
        enabled = enabled,
        error = error,
        style = style,
        borderWidth = borderWidth,
        height = height,
        padding = padding,
        onClick = onClick,
        loading = loading,
        contentStart = contentStart,
        horizontalArrangement = horizontalArrangement,
        contentEnd = contentEnd
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, style = textStyle)
            subtitle?.let {
                Text(text = subtitle, style = subtitleStyle)
            }
        }
    }
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    title: String = "Title",
    subtitle: String? = null,
    iconStart: Int? = null,
    iconEnd: Int? = null,
    iconPacked: Boolean = true,
    style: ButtonColorStyle = LocalButtonColor.current.primary,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    cornerRadius: Dp = LocalRadius.current.medium,
    iconSize: Dp = LocalSize.current.icon,
    enabled: Boolean = true,
    error: Boolean = false,
    loading: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        loading = loading,
        style = style,
        error = error,
        horizontalArrangement = horizontalArrangement,
        cornerRadius = cornerRadius,
        enabled = enabled,
        onClick = onClick,
        contentStart = {
            iconStart?.let {
                Icon(iconStart, iconSize)
                val modifier = if (iconPacked) {
                    Modifier.width(LocalPadding.current.medium)
                } else Modifier.weight(1f)
                Spacer(modifier = modifier)
            }
        },
        contentEnd = {
            iconEnd?.let {
                val modifier = if (iconPacked) {
                    Modifier.width(LocalPadding.current.medium)
                } else Modifier.weight(1f)
                Spacer(modifier = modifier)
                Icon(iconEnd, iconSize)
            }
        }
    )
}


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    style: ButtonColorStyle = LocalButtonColor.current.primary,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    cornerRadius: Dp = LocalRadius.current.medium,
    iconSize: Dp = LocalSize.current.icon,
    enabled: Boolean = true,
    height: Dp = LocalSize.current.control,
    error: Boolean = false,
    loading: Boolean = false,
    borderWidth: Dp? = null,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.size(height, height),
        loading = loading,
        style = style,
        error = error,
        horizontalArrangement = horizontalArrangement,
        cornerRadius = cornerRadius,
        enabled = enabled,
        onClick = onClick,
        borderWidth = borderWidth,
        content = {
            icon?.let {
                Icon(icon, iconSize)
            }
        }
    )
}


@Composable
fun IconOutlineTextButton(
    modifier: Modifier = Modifier,
    title: String = "Title",
    subtitle: String? = null,
    iconStart: Int? = null,
    iconEnd: Int? = null,
    iconPacked: Boolean = true,
    style: ButtonColorStyle = LocalButtonColor.current.outline,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    cornerRadius: Dp = LocalRadius.current.medium,
    iconSize: Dp = LocalSize.current.icon,
    enabled: Boolean = true,
    loading: Boolean = false,
    error: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        loading = loading,
        style = style,
        error = error,
        horizontalArrangement = horizontalArrangement,
        cornerRadius = cornerRadius,
        enabled = enabled,
        onClick = onClick,
        borderWidth = LocalBorder.current.medium,
        contentStart = {
            iconStart?.let {
                Icon(iconStart, iconSize)
                Spacer(modifier = Modifier.apply {
                    if (iconPacked) {
                        width(LocalPadding.current.medium)
                    } else weight(1f)
                })
            }
        },
        contentEnd = {
            iconEnd?.let {
                Spacer(modifier = Modifier.apply {
                    if (iconPacked) {
                        width(LocalPadding.current.medium)
                    } else weight(1f)
                })
                Icon(iconEnd, iconSize)
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun PreviewIconButtonWithText() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(vertical = 48.dp, horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        IconTextButton(
            title = "icon packed",
            iconStart = android.R.drawable.ic_menu_add,
        )
        Spacer(Modifier.size(LocalPadding.current.medium))

        IconTextButton(
            iconPacked = false,
            error = true,
            title = "icon spread with error",
            iconStart = android.R.drawable.ic_menu_add,
            iconEnd = android.R.drawable.ic_menu_send,
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        IconTextButton(
            title = "Secondary Button",
            style = LocalButtonColor.current.secondary,
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        var loading by remember { mutableStateOf(false) }

        IconTextButton(
            title = "Loading Button",
            loading = loading,
            onClick = {
                loading = !loading
            }
        )

        Spacer(Modifier.size(LocalPadding.current.medium))
        IconOutlineTextButton(
            title = "Error Outline Button",
            error = true,
            subtitle = "subtitle",
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        IconOutlineTextButton(
            title = "Transparent Button",
            loading = true,
            subtitle = "subtitle",
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        IconButton(
            error = true,
            icon = android.R.drawable.ic_menu_add,
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        IconButton(
            loading = loading,
            cornerRadius = LocalRadius.current.full,
            icon = android.R.drawable.ic_menu_add,
        )
        Spacer(Modifier.size(LocalPadding.current.medium))
        IconOutlineTextButton(
            title = "Full radius outline",
            subtitle = "subtitle",
            cornerRadius = LocalRadius.current.full
        )
    }
}
