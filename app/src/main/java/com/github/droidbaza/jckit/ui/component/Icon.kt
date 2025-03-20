package com.github.droidbaza.jckit.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.droidbaza.jckit.ui.theme.LocalContentColor
import com.github.droidbaza.jckit.ui.theme.LocalSize

@Composable
fun painterSafeResource(@DrawableRes id: Int): Painter {
    return if (id != 0) {
        painterResource(id = id)
    } else {
        object : Painter() {
            override val intrinsicSize: Size
                get() = Size.Zero

            override fun DrawScope.onDraw() {}
        }
    }
}

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = LocalSize.current.icon,
    tint: Color = LocalContentColor.current
) {
    val colorFilter =
        remember(tint) { if (tint == Color.Unspecified) null else ColorFilter.tint(tint) }
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(painter, size)
            .paint(painter, colorFilter = colorFilter, contentScale = ContentScale.Fit)
            .then(semantics)
    )
}

@Composable
fun Icon(
    resId: Int?,
    size: Dp = LocalSize.current.icon,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    resId ?: return
    val painter = painterSafeResource(resId)
    val colorFilter =
        remember(tint) { if (tint == Color.Unspecified) null else ColorFilter.tint(tint) }
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(painter, size)
            .paint(painter, colorFilter = colorFilter, contentScale = ContentScale.Fit)
            .then(semantics)
    )
}


@Composable
fun Icon(
    painter: Painter,
    tint: ColorProducer?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }

    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeForColorProducer(painter)
            .drawBehind {
                with(painter) {
                    draw(size = size, colorFilter = tint?.invoke()?.let { ColorFilter.tint(it) })
                }
            }
            .then(semantics)
    )
}

private fun Modifier.defaultSizeFor(painter: Painter, size: Dp) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            Modifier.size(size)
        } else {
            Modifier
        }
    )

private fun Modifier.defaultSizeForColorProducer(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            Modifier.size(24.dp)
        } else {
            val intrinsicSize = painter.intrinsicSize
            val srcWidth = intrinsicSize.width

            val srcHeight = intrinsicSize.height

            Modifier.layout { measurable, _ ->
                val placeable =
                    measurable.measure(Constraints.fixed(srcWidth.toInt(), srcHeight.toInt()))
                layout(placeable.width, placeable.height) { placeable.place(0, 0) }
            }
        }
    )

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()