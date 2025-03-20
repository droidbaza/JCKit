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
fun RadioButton(
    text: String,
    subText: String?,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .size(26.dp) // Чуть больше, чтобы влезло свечение
                .padding(4.dp)
        ) {
            if (selected) {
                // Свечение вокруг
                drawCircle(
                    color = Color.Blue.copy(alpha = 0.2f),
                    radius = size.minDimension / 2 + 4.dp.toPx()
                )
            }
            // Внешний круг
            drawCircle(
                color = if (selected) Color.Blue else Color.Gray,
                style = Stroke(width = 2.dp.toPx())
            )
            // Внутренний круг (если выбран)
            if (selected) {
                drawCircle(
                    color = Color.Blue,
                    radius = size.minDimension / 4
                )
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text, fontWeight = FontWeight.Bold)
            subText?.let {
                Text(it, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}


@Preview
@Composable
fun CustomRadioButtonPreview() {
    var selected by remember { mutableStateOf("option1") }

    Column {
        RadioButton(
            text = "Selected radiobutton",
            subText = "Set by default",
            selected = selected == "option1",
            onSelected = { selected = "option1" }
        )
        RadioButton(
            text = "Unselected state",
            subText = "This is Radio button",
            selected = selected == "option2",
            onSelected = { selected = "option2" }
        )
    }
}
