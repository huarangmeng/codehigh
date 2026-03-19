package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.lexer.LanguageRegistry
import com.hrm.codehigh.theme.CodeTheme
import com.hrm.codehigh.theme.LocalCodeTheme

/**
 * 行内代码组件，用于在文本流中渲染行内代码片段。
 * 对外公开。
 *
 * @param text 代码文本
 * @param modifier Compose Modifier
 * @param theme 代码主题，默认使用 LocalCodeTheme
 */
@Composable
public fun InlineCode(
    text: String,
    modifier: Modifier = Modifier,
    theme: CodeTheme = LocalCodeTheme.current
) {
    val highlightedText = remember(text, theme) {
        val lexer = LanguageRegistry.getOrPlain("")
        val tokens = lexer.tokenize(text)
        buildHighlightedString(tokens, theme)
    }

    BasicText(
        text = highlightedText,
        style = TextStyle(
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace,
            background = theme.background.copy(alpha = 0.8f)
        ),
        modifier = modifier
            .background(theme.background.copy(alpha = 0.8f))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}
