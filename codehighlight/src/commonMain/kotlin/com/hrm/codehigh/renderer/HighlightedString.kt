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

/**
 * 将 Token 列表按行拆分，返回每行对应的高亮 AnnotatedString 列表。
 * 行数与 [sourceLines] 严格对应，保证行号与代码行完全对齐。
 * 标记为 internal，仅供渲染层内部使用。
 *
 * @param sourceLines 原始代码按行拆分的列表
 * @param tokens Token 列表（跨行 token 会被正确拆分）
 * @param theme 代码主题
 * @return 每行对应的 AnnotatedString 列表，长度与 sourceLines 相同
 */
internal fun buildLineHighlights(
    sourceLines: List<String>,
    tokens: List<CodeToken>,
    theme: CodeTheme
): List<AnnotatedString> {
    return buildLineRenders(
        sourceLines = sourceLines,
        tokens = tokens,
        theme = theme
    ).map { it.text }
}

internal fun buildLineRenders(
    sourceLines: List<String>,
    tokens: List<CodeToken>,
    theme: CodeTheme,
    language: String = "",
    highlightedLines: Set<Int> = emptySet()
): List<CodeLineRender> {
    // 将所有 token 文本拼接后按 \n 拆分，重新映射到每行
    // 策略：先构建整体 AnnotatedString，再按换行符切割
    val full = buildHighlightedString(tokens, theme)
    val result = mutableListOf<AnnotatedString>()
    var offset = 0
    for (i in sourceLines.indices) {
        val lineLen = sourceLines[i].length
        val end = (offset + lineLen).coerceAtMost(full.length)
        result.add(
            if (offset <= full.length) full.subSequence(offset, end) as AnnotatedString
            else AnnotatedString("")
        )
        // 跳过换行符（\n 占 1 个字符）
        offset = end + 1
    }
    return result.mapIndexed { index, line ->
        CodeLineRender(
            text = line,
            kind = resolveLineKind(
                lineIndex = index,
                lineText = sourceLines.getOrElse(index) { "" },
                language = language,
                highlightedLines = highlightedLines
            )
        )
    }
}

/**
 * 构建带颜色 Span 的 AnnotatedString。
 * 对外公开，供尺寸测量和渲染使用。
 *
 * @param tokens Token 列表
 * @param theme 代码主题
 * @return 带颜色 Span 的 AnnotatedString
 */
public fun buildHighlightedString(
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
            lineText.startsWith("diff ") || lineText.startsWith("index ") || lineText.startsWith("+++") || lineText.startsWith("---") -> CodeLineKind.DIFF_META_HEADER
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
