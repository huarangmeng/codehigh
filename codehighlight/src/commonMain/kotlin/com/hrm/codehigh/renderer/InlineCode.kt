package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InlineCode(
    text: String,
    modifier: Modifier = Modifier,
    style: InlineCodeStyle = InlineCodeDefaults.style(),
) {
    BasicText(
        text = text,
        style = style.textStyle,
        modifier = modifier
            .background(
                color = style.containerColor,
                shape = style.shape,
            )
            .then(
                if (style.borderColor != null && style.borderWidth.value > 0f) {
                    Modifier.border(
                        width = style.borderWidth,
                        color = style.borderColor,
                        shape = style.shape,
                    )
                } else {
                    Modifier
                }
            )
            .padding(style.contentPadding),
    )
}
