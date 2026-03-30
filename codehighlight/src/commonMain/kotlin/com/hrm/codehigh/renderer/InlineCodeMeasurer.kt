package com.hrm.codehigh.renderer

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.lexer.LanguageRegistry
import com.hrm.codehigh.theme.CodeTheme

/**
 * 行内代码尺寸测量结果
 */
data class InlineCodeSize(
    /**
     * 宽度（像素）
     */
    val width: Float,
    /**
     * 高度（像素）
     */
    val height: Float
) {
    /**
     * 获取宽度（dp）
     */
    fun widthDp(density: Density): Float = with(density) { width.toDp().value }

    /**
     * 获取高度（dp）
     */
    fun heightDp(density: Density): Float = with(density) { height.toDp().value }
}

/**
 * 测量行内代码的尺寸，用于外部占位和位置调整。
 * 此函数需要在 Compose 组合上下文中调用，以便获取正确的 TextMeasurer。
 * 对外公开。
 *
 * @param text 代码文本
 * @param language 语言标识符（可选，默认使用 PlainTextLexer）
 * @param theme 代码主题
 * @param textStyle 文本样式（可选，提供默认值）
 * @param density 密度对象，用于 dp 转换
 * @param textMeasurer TextMeasurer 实例，用于测量文本
 * @param maxWidth 最大宽度限制（可选，用于文本换行）
 * @return 行内代码尺寸
 */
fun measureInlineCodeSize(
    text: String,
    language: String = "",
    theme: CodeTheme,
    textStyle: TextStyle = TextStyle(
        fontSize = 13.sp,
        fontFamily = FontFamily.Monospace
    ),
    density: Density,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    maxWidth: Float = Float.POSITIVE_INFINITY
): InlineCodeSize {
    val lexer = LanguageRegistry.getOrPlain(language)
    val tokens = lexer.tokenize(text)
    val annotatedString = buildHighlightedString(tokens, theme)

    return measureAnnotatedStringSize(
        annotatedString = annotatedString,
        textStyle = textStyle,
        density = density,
        maxWidth = maxWidth,
        paddingHorizontal = 4.dp,
        paddingVertical = 2.dp,
        textMeasurer = textMeasurer
    )
}

/**
 * 内部辅助函数：测量 AnnotatedString 的尺寸并添加 padding
 */
internal fun measureAnnotatedStringSize(
    annotatedString: AnnotatedString,
    textStyle: TextStyle,
    density: Density,
    maxWidth: Float,
    paddingHorizontal: androidx.compose.ui.unit.Dp,
    paddingVertical: androidx.compose.ui.unit.Dp,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
): InlineCodeSize {
    with(density) {
        val paddingHorizontalPx = paddingHorizontal.toPx()
        val paddingVerticalPx = paddingVertical.toPx()

        val maxWidthWithoutPadding = maxWidth - paddingHorizontalPx * 2

        val constraints = if (maxWidthWithoutPadding.isFinite() && maxWidthWithoutPadding > 0) {
            androidx.compose.ui.unit.Constraints(
                maxWidth = maxWidthWithoutPadding.toInt()
            )
        } else {
            androidx.compose.ui.unit.Constraints()
        }

        val layoutResult = textMeasurer.measure(
            text = annotatedString,
            style = textStyle,
            overflow = TextOverflow.Clip,
            softWrap = false,
            constraints = constraints
        )

        return InlineCodeSize(
            width = layoutResult.size.width + paddingHorizontalPx * 2,
            height = layoutResult.size.height + paddingVerticalPx * 2
        )
    }
}
