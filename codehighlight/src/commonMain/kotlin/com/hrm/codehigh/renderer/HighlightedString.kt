package com.hrm.codehigh.renderer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType
import com.hrm.codehigh.theme.CodeTheme
import com.hrm.codehigh.theme.safeColorFor

internal enum class CodeLineKind {
    NORMAL,
    HIGHLIGHTED,
    DIFF_ADDED,
    DIFF_REMOVED,
    DIFF_META_HEADER,
    DIFF_META_HUNK
}

internal data class CodeLineRender(
    val text: AnnotatedString,
    val kind: CodeLineKind
)

internal fun buildLineRenders(
    sourceLines: List<String>,
    tokens: List<CodeToken>,
    theme: CodeTheme,
    language: String = "",
    highlightedLines: Set<Int> = emptySet()
): List<CodeLineRender> {
    return buildLineRendersFromOffset(
        sourceLines = sourceLines,
        tokens = tokens,
        theme = theme,
        language = language,
        highlightedLines = highlightedLines,
        startLineIndex = 0,
        startCharOffset = 0,
    )
}

internal fun buildLineRendersFromOffset(
    sourceLines: List<String>,
    tokens: List<CodeToken>,
    theme: CodeTheme,
    language: String = "",
    highlightedLines: Set<Int> = emptySet(),
    startLineIndex: Int,
    startCharOffset: Int,
): List<CodeLineRender> {
    val visibleSourceLines = sourceLines.drop(startLineIndex)
    if (visibleSourceLines.isEmpty()) return emptyList()

    val visibleTokens = tokens.toRelativeTokens(startCharOffset)

    // 将所有 token 文本拼接后按 \n 拆分，重新映射到每行
    // 策略：先构建整体 AnnotatedString，再按换行符切割
    val full = buildHighlightedString(visibleTokens, theme)
    val result = mutableListOf<AnnotatedString>()
    var offset = 0
    for (i in visibleSourceLines.indices) {
        val lineLen = visibleSourceLines[i].length
        val end = (offset + lineLen).coerceAtMost(full.length)
        result.add(
            if (offset <= full.length) full.subSequence(offset, end)
            else AnnotatedString("")
        )
        // 跳过换行符（\n 占 1 个字符）
        offset = end + 1
    }
    return result.mapIndexed { index, line ->
        CodeLineRender(
            text = line,
            kind = resolveLineKind(
                lineIndex = startLineIndex + index,
                lineText = visibleSourceLines.getOrElse(index) { "" },
                language = language,
                highlightedLines = highlightedLines
            )
        )
    }
}

private fun List<CodeToken>.toRelativeTokens(startCharOffset: Int): List<CodeToken> {
    if (startCharOffset <= 0) return this
    val result = ArrayList<CodeToken>(size)
    for (token in this) {
        if (token.range.last < startCharOffset) continue
        val cut = (startCharOffset - token.range.first).coerceAtLeast(0)
        val text = if (cut == 0) token.text else token.text.drop(cut)
        if (text.isEmpty()) continue
        val start = (token.range.first - startCharOffset).coerceAtLeast(0)
        val end = start + text.length - 1
        result.add(
            CodeToken(
                type = token.type,
                text = text,
                range = start..end,
            )
        )
    }
    return result
}

/**
 * 构建带颜色 Span 的 AnnotatedString。
 * 对外公开，供尺寸测量和渲染使用。
 *
 * @param tokens Token 列表
 * @param theme 代码主题
 * @return 带颜色 Span 的 AnnotatedString
 */
fun buildHighlightedString(
    tokens: List<CodeToken>,
    theme: CodeTheme
): AnnotatedString {
    return buildAnnotatedString {
        for (token in tokens) {
            val color = theme.safeColorFor(token.type)
            val fontWeight = when (token.type) {
                TokenType.KEYWORD -> FontWeight.Bold
                else -> FontWeight.Normal
            }
            val fontStyle = when (token.type) {
                TokenType.COMMENT -> FontStyle.Italic
                else -> FontStyle.Normal
            }
            pushStyle(
                SpanStyle(
                    color = color,
                    fontWeight = fontWeight,
                    fontStyle = fontStyle
                )
            )
            append(token.text)
            pop()
        }
    }
}

internal fun resolveLineKind(
    lineIndex: Int,
    lineText: String,
    language: String,
    highlightedLines: Set<Int>
): CodeLineKind {
    if (language.lowercase() == "diff") {
        return when {
            lineText.startsWith("@@") -> CodeLineKind.DIFF_META_HUNK
            lineText.startsWith("diff ") || lineText.startsWith("index ") || lineText.startsWith("+++") || lineText.startsWith(
                "---"
            ) -> CodeLineKind.DIFF_META_HEADER

            lineText.startsWith("+") -> CodeLineKind.DIFF_ADDED
            lineText.startsWith("-") -> CodeLineKind.DIFF_REMOVED
            else -> CodeLineKind.NORMAL
        }
    }

    return if (lineIndex + 1 in highlightedLines) CodeLineKind.HIGHLIGHTED else CodeLineKind.NORMAL
}

internal fun CodeTheme.backgroundForLine(kind: CodeLineKind) = when (kind) {
    CodeLineKind.NORMAL -> Color.Transparent
    CodeLineKind.HIGHLIGHTED -> highlightedLineBackground
    CodeLineKind.DIFF_ADDED -> diffAddedLineBackground
    CodeLineKind.DIFF_REMOVED -> diffRemovedLineBackground
    CodeLineKind.DIFF_META_HEADER -> diffMetaLineBackground
    CodeLineKind.DIFF_META_HUNK -> diffMetaLineBackground
}
