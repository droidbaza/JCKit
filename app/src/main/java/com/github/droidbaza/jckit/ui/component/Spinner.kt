package com.github.droidbaza.jckit.ui.component


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.droidbaza.jckit.ui.theme.LocalBorder
import com.github.droidbaza.jckit.ui.theme.LocalContentColor
import kotlin.math.min

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = LocalBorder.current.medium,
    color: Color = LocalContentColor.current,
    durationMillis: Int = 300
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RotationTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotation)
        ) {
            val size = min(size.width, size.height)
            val strokeWidthPx = strokeWidth.toPx()
            val startAngle = 0f
            val sweepAngle = 270f // Дуга 3/4 круга
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidthPx, cap = StrokeCap.Round),
                size = Size(size, size)
            )
        }
    }
}


@Preview
@Composable
fun PreviewProgressIndicator() {
    Spinner()
}