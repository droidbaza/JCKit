package com.github.droidbaza.jckit.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.github.droidbaza.jckit.ui.theme.LocalColor
import com.github.droidbaza.jckit.ui.theme.LocalContentColor


@Composable
@NonRestartableComposable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = LocalColor.current.surface,
    contentColor: Color = LocalColor.current.onPrimary,
    shadowElevation: Dp? = null,
    border: BorderStroke? = null,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier = modifier
                    .surface(
                        shape = shape,
                        backgroundColor = color,
                        border = border,
                        shadowElevation = with(LocalDensity.current) { shadowElevation?.toPx()?:0f }
                    )
                    .semantics(mergeDescendants = false) {
                        @Suppress("DEPRECATION")
                        isContainer = true
                    }
                    .pointerInput(Unit) {},
            propagateMinConstraints = true,
            content = content
        )
    }
}


@Composable
@NonRestartableComposable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = LocalColor.current.surface,
    contentColor: Color = LocalColor.current.onPrimary,
    shadowElevation: Dp? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier =
                modifier
                    .surface(
                        shape = shape,
                        backgroundColor = color,
                        border = border,
                        shadowElevation = with(LocalDensity.current) {
                            shadowElevation?.toPx() ?: 0f
                        }
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = indication,
                        enabled = enabled,
                        onClick = onClick
                    ),
            propagateMinConstraints = true,
        ){
            content(this)
        }
    }
}



@Stable
fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?=null,
    shadowElevation: Float = 0f,
) =
    this
        .then(
            if (shadowElevation > 0f) {
                Modifier.graphicsLayer(
                    shadowElevation = shadowElevation,
                    shape = shape,
                    clip = false
                )
            } else {
                Modifier
            }
        )
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .background(color = backgroundColor, shape = shape)
        .clip(shape)

