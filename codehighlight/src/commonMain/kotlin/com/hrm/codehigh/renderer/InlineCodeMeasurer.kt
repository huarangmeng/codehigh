package com.hrm.codehigh.renderer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

data class InlineCodeSize(
    val width: Float,
    val height: Float,
) {
    fun widthDp(density: Density): Float = with(density) { width.toDp().value }

    fun heightDp(density: Density): Float = with(density) { height.toDp().value }
}

fun measureInlineCodeSize(
    text: String,
    language: String = "",
    style: InlineCodeStyle,
    density: Density,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    maxWidth: Float = Float.POSITIVE_INFINITY,
): InlineCodeSize {
    val annotatedString = AnnotatedString(text)

    return measureAnnotatedStringSize(
        annotatedString = annotatedString,
        textStyle = style.textStyle,
        density = density,
        maxWidth = maxWidth,
        contentPadding = style.contentPadding,
        borderWidth = style.borderWidth,
        textMeasurer = textMeasurer,
    )
}

internal fun measureAnnotatedStringSize(
    annotatedString: AnnotatedString,
    textStyle: TextStyle,
    density: Density,
    maxWidth: Float,
    contentPadding: PaddingValues,
    borderWidth: Dp = 0.dp,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
): InlineCodeSize {
    with(density) {
        val horizontalPaddingPx =
            contentPadding.calculateLeftPadding(LayoutDirection.Ltr).toPx() +
                contentPadding.calculateRightPadding(LayoutDirection.Ltr).toPx()
        val verticalPaddingPx =
            contentPadding.calculateTopPadding().toPx() +
                contentPadding.calculateBottomPadding().toPx()
        val horizontalBorderPx = borderWidth.toPx() * 2f
        val verticalBorderPx = borderWidth.toPx() * 2f
        val horizontalDecorationPx = horizontalPaddingPx + horizontalBorderPx
        val verticalDecorationPx = verticalPaddingPx + verticalBorderPx

        val maxWidthWithoutDecoration = maxWidth - horizontalDecorationPx

        val constraints = if (maxWidthWithoutDecoration.isFinite() && maxWidthWithoutDecoration > 0) {
            androidx.compose.ui.unit.Constraints(
                maxWidth = maxWidthWithoutDecoration.toInt(),
            )
        } else {
            androidx.compose.ui.unit.Constraints()
        }

        val layoutResult = textMeasurer.measure(
            text = annotatedString,
            style = textStyle,
            overflow = TextOverflow.Clip,
            softWrap = false,
            constraints = constraints,
        )

        return InlineCodeSize(
            width = layoutResult.size.width + horizontalDecorationPx,
            height = layoutResult.size.height + verticalDecorationPx,
        )
    }
}
