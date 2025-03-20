package com.github.droidbaza.jckit.ui.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Checkbox(
    text: String,
    subText: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .size(26.dp)
                .padding(4.dp)
        ) {
            val checkboxSize = size.minDimension * 0.8f
            val glowSize = checkboxSize + 12.dp.toPx() // Свечение должно быть больше чекбокса

            if (checked) {
                // Свечение вокруг (по центру)
                drawRoundRect(
                    color = Color.Blue.copy(alpha = 0.3f),
                    size = Size(glowSize, glowSize),
                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx()),
                    topLeft = Offset((size.width - glowSize) / 2, (size.height - glowSize) / 2)
                )
            }
            // Контур квадрата
            drawRoundRect(
                color = if (checked) Color.Blue else Color.Gray,
                style = Stroke(width = 2.dp.toPx()),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
            // Галочка (если выбрано)
            if (checked) {
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.5f)
                    lineTo(size.width * 0.4f, size.height * 0.75f)
                    lineTo(size.width * 0.8f, size.height * 0.25f)
                }
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text, fontWeight = FontWeight.Bold)
            subText?.let {
                Text(it, fontSize = 12.sp, color = Color.Blue)
            }
        }
    }
}

@Preview
@Composable
fun CustomCheckboxPreview() {
    var checked by remember { mutableStateOf(true) }
    var unchecked by remember { mutableStateOf(false) }

    Column {
        Checkbox(
            text = "Checked false",
            subText = "Hit me one more time",
            checked = unchecked,
            onCheckedChange = { unchecked = it }
        )
        Checkbox(
            text = "Selected checked",
            subText = "1 item selected",
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }
}