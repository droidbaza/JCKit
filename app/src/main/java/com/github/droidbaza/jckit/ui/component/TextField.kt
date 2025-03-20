package com.github.droidbaza.jckit.ui.component

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.droidbaza.jckit.ui.theme.JCKitTheme
import com.github.droidbaza.jckit.ui.theme.LocalBorder
import com.github.droidbaza.jckit.ui.theme.LocalContentColor
import com.github.droidbaza.jckit.ui.theme.LocalPadding
import com.github.droidbaza.jckit.ui.theme.LocalRadius
import com.github.droidbaza.jckit.ui.theme.LocalSize
import com.github.droidbaza.jckit.ui.theme.LocalTheme
import com.github.droidbaza.jckit.ui.theme.LocalTypography
import com.github.droidbaza.jckit.ui.theme.ProvideContentColorTextStyle
import com.github.droidbaza.jckit.ui.theme.active
import com.github.droidbaza.jckit.ui.theme.disabled

@Immutable
data class TextFieldColorStyle(
    val color: Color = Color.Unspecified,
    val disabledColor: Color = color.disabled,
    val focusColor: Color = color.active,
    val errorColor: Color = focusColor,

    val inputColor: Color = Color.Unspecified,
    val disabledInputColor: Color = inputColor.disabled,
    val focusInputColor: Color = inputColor.active,
    val errorInputColor: Color = Color.Unspecified,

    val labelColor: Color = Color.Unspecified,
    val disabledLabelColor: Color = labelColor.disabled,
    val focusLabelColor: Color = labelColor.active,
    val errorLabelColor: Color = Color.Unspecified,

    val placeholderColor: Color = labelColor,
    val disabledPlaceholderColor: Color = placeholderColor.disabled,

    val borderColor: Color = Color.Unspecified,
    val disabledBorderColor: Color = borderColor.disabled,
    val focusBorderColor: Color = borderColor.active,
    val errorBorderColor: Color = errorColor,

    val cursorColor: Color = Color.Unspecified,
    val iconStartTint: Color = inputColor,
    val iconEndTint: Color = iconStartTint,
    val disabledIconStartTint: Color = iconStartTint.disabled,
    val disabledIconEndTint: Color = iconEndTint.disabled,

    val errorMsgColor: Color = errorColor,
) {

    @Stable
    internal fun getInputColor(
        focused: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false,
    ): Color = when {
        !enabled -> disabledInputColor
        error -> errorInputColor
        focused -> focusInputColor
        else -> inputColor
    }

    @Stable
    internal fun getLabelColor(
        focused: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false,
    ): Color = when {
        !enabled -> disabledLabelColor
        // error -> errorLabelColor
        focused -> focusLabelColor
        else -> labelColor
    }

    @Stable
    internal fun getBorderColor(
        focused: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false,
    ): Color = when {
        !enabled -> disabledBorderColor
        error -> errorBorderColor
        focused -> focusBorderColor
        else -> borderColor
    }

    @Stable
    internal fun getPlaceholderColor(
        enabled: Boolean = true,
    ): Color = when {
        !enabled -> disabledPlaceholderColor
        else -> placeholderColor
    }

    @Stable
    internal fun getColor(
        focused: Boolean = false,
        enabled: Boolean = true,
        error: Boolean = false
    ): Color = when {
        !enabled -> disabledColor
        error -> errorColor
        focused -> focusColor
        else -> color
    }

    fun getIconStartTint(enabled: Boolean): Color = when {
        enabled -> iconStartTint
        else -> disabledIconStartTint
    }

    fun getIconEndTint(enabled: Boolean): Color = when {
        enabled -> iconEndTint
        else -> disabledIconEndTint
    }
}

@Immutable
data class TextFieldColorStyles(
    val primary: TextFieldColorStyle,
    val secondary: TextFieldColorStyle,
    val outline: TextFieldColorStyle,
)

val LocalTextFieldColor = compositionLocalOf<TextFieldColorStyles> {
    error("No TextFieldColor provided")
}


@Composable
private fun Decoration(contentColor: Color, textStyle: TextStyle, content: @Composable () -> Unit) =
    ProvideContentColorTextStyle(contentColor, textStyle, content)


@Composable
private fun Decoration(contentColor: Color, content: @Composable () -> Unit) =
    CompositionLocalProvider(LocalContentColor provides contentColor, content = content)


@Composable
fun rememberTextFieldColor(
    theme: JCKitTheme = LocalTheme.current
): TextFieldColorStyles {
    return with(theme.color) {
        TextFieldColorStyles(
            primary = TextFieldColorStyle(
                color = background,
                inputColor = onSurface,
                labelColor = onSurfaceSecondary,
                borderColor = onBackground,
                cursorColor = onBackground,
                placeholderColor = onSurfaceLight,
                iconStartTint = error,
                errorBorderColor = error,
                errorMsgColor = error,
            ),
            secondary = TextFieldColorStyle(
                color = background,
                inputColor = onSurface,
                labelColor = onSurfaceSecondary,
                borderColor = onBackground,
                cursorColor = onBackground,
                placeholderColor = onSurfaceLight,
                iconStartTint = error,
                errorBorderColor = error
            ),
            outline = TextFieldColorStyle(
                color = background,
                inputColor = onSurface,
                labelColor = onSurfaceSecondary,
                borderColor = onBackground,
                cursorColor = onBackground,
                placeholderColor = onSurfaceLight,
                iconStartTint = error,
                errorBorderColor = error
            ),
        )
    }
}


@Composable
fun rememberKeyboardVisible(): State<Boolean> {
    val context = LocalContext.current
    val rootView = remember { (context as Activity).window.decorView }
    val keyboardVisible = remember { mutableStateOf(false) }
    val currentKeyboardVisibleState = rememberUpdatedState(keyboardVisible.value)

    DisposableEffect(rootView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > screenHeight * 0.15

            if (currentKeyboardVisibleState.value != isKeyboardVisible) {
                keyboardVisible.value = isKeyboardVisible
            }
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return keyboardVisible

}


@Composable
fun TextField(
    input: String = "",
    label: String? = null,
    placeHolder: String? = null,
    error: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    style: TextFieldColorStyle = LocalTextFieldColor.current.primary,
    readOnly: Boolean = false,
    border: Dp? = LocalBorder.current.medium,
    radius: Dp = LocalRadius.current.medium,
    height: Dp = LocalSize.current.control,
    horizontalPadding: Dp = LocalPadding.current.medium,
    verticalPadding: Dp = LocalPadding.current.small,
    inputStyle: TextStyle = LocalTypography.current.input,
    labelStyle: TextStyle = LocalTypography.current.caption,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    contentStart: (@Composable () -> Unit)? = null,
    contentEnd: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val isKeyboardVisible by rememberKeyboardVisible()

    val color = style.getColor(focused, enabled, error)
    val borderColor = style.getBorderColor(focused, enabled, error)
    val placeHolderColor = style.getPlaceholderColor(enabled)
    val labelColor = style.getLabelColor(focused, enabled, error)
    val inputColor = style.getInputColor(focused, enabled, error)
    val iconStartTint = style.getIconStartTint(enabled)
    val iconEndTint = style.getIconEndTint(enabled)
    val cursorColor = style.cursorColor

    val full = LocalRadius.current.full
    val shape = remember(radius) {
        if (radius == full) CircleShape else RoundedCornerShape(radius)
    }
    val borderStroke = remember(borderColor) {
        if (border != null) BorderStroke(border, borderColor) else null
    }
    val inputActivated = focused || input.isNotEmpty()
    val labelFontSize by animateFloatAsState(
        targetValue = if (inputActivated) labelStyle.fontSize.value else inputStyle.fontSize.value,
        animationSpec = tween(100)
    )
    val inputSize = inputStyle.fontSize.value

    val labelSize = labelFontSize + verticalPadding.value / 2
    val labelPosition = -height.value / 2 + inputSize/2
    val labelOffset by animateFloatAsState(
        targetValue = if (inputActivated) labelPosition else 0f,
        animationSpec = tween(100)
    )
    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            focusManager.clearFocus()
        }
    }

    Column {
        BasicTextField(
            modifier = modifier.focusable(),
            value = input,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = inputStyle.copy(color = inputColor),
            cursorBrush = SolidColor(cursorColor),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(height)
                        .surface(border = borderStroke, backgroundColor = color, shape = shape)
                        .padding(horizontal = horizontalPadding, vertical = verticalPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Decoration(iconStartTint) {
                        contentStart?.invoke()
                    }
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (label != null) {
                            Text(
                                text = label,
                                style = labelStyle.copy(
                                    color = labelColor,
                                    fontSize = labelFontSize.sp
                                ),
                                modifier = Modifier.graphicsLayer { translationY = labelOffset }
                            )
                        }
                        if (inputActivated) {
                            Column {
                                if (label != null) {
                                    Spacer(Modifier.padding(top = labelSize.dp))
                                }
                                Box {
                                    if (input.isEmpty() && placeHolder != null) {
                                        Text(
                                            text = placeHolder,
                                            style = inputStyle.copy(color = placeHolderColor)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    }
                    Decoration(iconEndTint) {
                        contentEnd?.invoke()
                    }
                }
            }
        )
    }
    if (error && !errorMessage.isNullOrEmpty()) {
        Text(
            text = errorMessage,
            style = labelStyle,
            color = style.errorMsgColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Composable
fun IconTextField(
    input: String = "",
    label: String? = null,
    @DrawableRes
    iconStart: Int? = null,
    @DrawableRes
    iconEnd: Int? = null,
    placeHolder: String? = null,
    error: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    style: TextFieldColorStyle = LocalTextFieldColor.current.primary,
    readOnly: Boolean = false,
    border: Dp? = LocalBorder.current.medium,
    radius: Dp = LocalRadius.current.medium,
    height: Dp = LocalSize.current.control,
    horizontalPadding: Dp = LocalPadding.current.medium,
    verticalPadding: Dp = LocalPadding.current.small,
    inputStyle: TextStyle = LocalTypography.current.input,
    labelStyle: TextStyle = LocalTypography.current.caption,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    onValueChange: (String) -> Unit = {},
){
    TextField(
        maxLines = maxLines,
        minLines = minLines,
        labelStyle = labelStyle,
        inputStyle = inputStyle,
        interactionSource = interactionSource,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        verticalPadding = verticalPadding,
        horizontalPadding = horizontalPadding,
        border = border,
        radius = radius,
        height = height,
        enabled = enabled,
        modifier = modifier,
        style = style,
        input = input,
        readOnly = readOnly,
        label = label,
        error = error,
        placeHolder =placeHolder,
        errorMessage =errorMessage,
        contentStart = {
            iconStart?.let {
                Icon(iconStart)
            }
        },
        contentEnd = {
            iconEnd?.let {
                Icon(iconEnd)
            }
        }
    )
}

@Composable
fun IconTextField(
    input: String = "",
    label: String? = null,
    iconStart: ImageVector? = null,
    iconEnd: ImageVector? = null,
    placeHolder: String? = null,
    error: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    style: TextFieldColorStyle = LocalTextFieldColor.current.primary,
    readOnly: Boolean = false,
    border: Dp? = LocalBorder.current.medium,
    radius: Dp = LocalRadius.current.medium,
    height: Dp = LocalSize.current.control,
    horizontalPadding: Dp = LocalPadding.current.medium,
    verticalPadding: Dp = LocalPadding.current.small,
    inputStyle: TextStyle = LocalTypography.current.input,
    labelStyle: TextStyle = LocalTypography.current.caption,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    onValueChange: (String) -> Unit = {},
){

    TextField(
        maxLines = maxLines,
        minLines = minLines,
        labelStyle = labelStyle,
        inputStyle = inputStyle,
        interactionSource = interactionSource,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        verticalPadding = verticalPadding,
        horizontalPadding = horizontalPadding,
        border = border,
        radius = radius,
        height = height,
        enabled = enabled,
        modifier = modifier,
        style = style,
        input = input,
        readOnly = readOnly,
        label = label,
        error = error,
        placeHolder =placeHolder,
        errorMessage =errorMessage,
        contentStart = {
           iconStart?.let {
               Icon(iconStart)
           }
        },
        contentEnd = {
            iconEnd?.let {
                Icon(iconEnd)
            }
        }
    )

}
@Preview(showSystemUi = true)
@Composable
fun PreviewTextFields() {
    Column(modifier = Modifier.padding(vertical = 48.dp, horizontal = 24.dp)) {
        var inputText by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }

        TextField(
            input = inputText,
            label = "Введите текст",
            error = isError,
            placeHolder = "Запрос,Описание,И тд",
            errorMessage = if (isError) "Максимум 10 символов" else null,
            contentStart = {
                Icon(imageVector = Icons.Default.Person)
            },
        ) {
            isError = it.length > 10
            inputText = it
        }
    }
}


