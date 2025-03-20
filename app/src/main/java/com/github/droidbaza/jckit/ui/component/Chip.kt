package com.github.droidbaza.jckit.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    text: String,
    selected: Boolean,
    showCloseIcon: Boolean = true,
    onClick: () -> Unit,
    onCloseClick: (() -> Unit)? = null
) {
    val borderColor = if (selected) Color.Blue else Color.Gray.copy(alpha = 0.5f)
    val textColor = if (selected) Color.Blue else Color.Black

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = borderColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(text, color = textColor, fontWeight = FontWeight.Bold)
            if (showCloseIcon && onCloseClick != null) {
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(onClick = onCloseClick)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChipPreview() {

    Column {
        Spacer(Modifier.padding(40.dp))
        Chip(text = "Label", selected = false, onClick = {}, onCloseClick = {})
        Chip(text = "Iconic", selected = false, showCloseIcon = false, onClick = {})
        Chip(text = "Active", selected = true, onClick = {})
    }
}