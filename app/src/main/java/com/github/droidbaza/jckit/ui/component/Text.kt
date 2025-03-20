package com.github.droidbaza.jckit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.github.droidbaza.jckit.ui.theme.LocalContentColor
import com.github.droidbaza.jckit.ui.theme.LocalPadding
import com.github.droidbaza.jckit.ui.theme.LocalTypography
import com.github.droidbaza.jckit.ui.theme.disabled

val LocalTitleColor = compositionLocalOf { Color.Black }
val LocalSubtitleColor = compositionLocalOf { Color.Gray }

@Composable
fun TextDecoration(
    titleColor: Color,
    subtitleColor: Color,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTitleColor provides titleColor,
        LocalSubtitleColor provides subtitleColor,
        content = content
    )
}


@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTypography.current.body
) {


    BasicText(
        text,
        modifier,
        style.merge(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign ?: TextAlign.Unspecified,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        ),
        onTextLayout,
        overflow,
        softWrap,
        maxLines,
        minLines
    )
}


@Composable
fun TextLabel(
    label: String? = null,
    title: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    labelColor: Color = LocalSubtitleColor.current,
    titleColor: Color = LocalTitleColor.current,
    labelStyle: TextStyle = LocalTypography.current.caption,
    titleStyle: TextStyle = LocalTypography.current.body,
) {
    val titleColor = remember(enabled) {
        if (enabled) titleColor else titleColor.disabled
    }
    val subtitleColor = remember(enabled) {
        if (enabled) labelColor else labelColor.disabled
    }
    Column(
        modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        if (label != null) {
            Text(label, color = subtitleColor, style = labelStyle)
        }
        if (title != null) {
            Text(title, color = titleColor, style = titleStyle)
        }
    }
}

@Composable
fun TextSubtitle(
    title: String? = null,
    subtitle: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    titleColor: Color = LocalTitleColor.current,
    subtitleColor: Color = LocalSubtitleColor.current,
    titleStyle: TextStyle = LocalTypography.current.body,
    subtitleStyle: TextStyle = LocalTypography.current.caption,
) {
    val titleColor = remember(enabled) {
        if (enabled) titleColor else titleColor.disabled
    }
    val subtitleColor = remember(enabled) {
        if (enabled) subtitleColor else subtitleColor.disabled
    }
    Column(
        modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        if (title != null) {
            Text(title, color = titleColor, style = titleStyle)
        }
        if (subtitle != null) {
            Text(subtitle, color = subtitleColor, style = subtitleStyle)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Previews() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(LocalPadding.current.large),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.size(LocalPadding.current.medium))
        TextSubtitle(title = "Titlwefqwefqwefe", subtitle = "subtitle")

        Spacer(Modifier.size(LocalPadding.current.medium))
        TextSubtitle(subtitle = "subtitle")

        Spacer(Modifier.size(LocalPadding.current.medium))
        TextSubtitle(title = "Title")
    }
}