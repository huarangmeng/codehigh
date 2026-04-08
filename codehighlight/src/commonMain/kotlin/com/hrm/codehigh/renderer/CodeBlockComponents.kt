package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.theme.CodeTheme

/**
 * 行号列组件，显示代码行号。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 *
 * @param lineCount 总行数
 * @param theme 代码主题
 * @param startLine 起始行号（默认为 1）
 */
@Composable
internal fun LineNumberColumn(
    lineCount: Int,
    theme: CodeTheme,
    startLine: Int = 1
) {
    Column(
        modifier = Modifier
            .background(theme.background)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        for (i in startLine until startLine + lineCount) {
            BasicText(
                text = i.toString(),
                style = TextStyle(
                    color = theme.colorFor(com.hrm.codehigh.ast.TokenType.COMMENT).copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

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
