package com.hrm.codehigh.renderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.theme.CodeTheme

@Composable
internal fun CodeBlockHeaderLabels(
    title: String,
    language: String,
    theme: CodeTheme,
    modifier: Modifier = Modifier
) {
    if (title.isBlank() && language.isBlank()) return

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        CodeBlockTitle(title = title, theme = theme)
        LanguageLabel(language = language, theme = theme)
    }
}

@Composable
internal fun CodeBlockTitle(
    title: String,
    theme: CodeTheme,
    modifier: Modifier = Modifier
) {
    if (title.isBlank()) return

    BasicText(
        text = title,
        style = TextStyle(
            color = theme.colorFor(com.hrm.codehigh.ast.TokenType.PLAIN).copy(alpha = 0.92f),
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium
        ),
        modifier = modifier.padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

/**
 * 语言标签组件，显示代码语言名称。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 *
 * @param language 语言标识符
 * @param theme 代码主题
 */
@Composable
internal fun LanguageLabel(
    language: String,
    theme: CodeTheme,
    modifier: Modifier = Modifier
) {
    if (language.isBlank()) return

    BasicText(
        text = language.lowercase(),
        style = TextStyle(
            color = theme.colorFor(com.hrm.codehigh.ast.TokenType.COMMENT).copy(alpha = 0.7f),
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
        ),
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
