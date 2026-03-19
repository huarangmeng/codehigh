package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType
import com.hrm.codehigh.stream.IncrementalHighlighter
import com.hrm.codehigh.theme.CodeTheme
import com.hrm.codehigh.theme.LocalCodeTheme
import kotlinx.coroutines.delay

/**
 * 代码块渲染组件，唯一对外渲染入口。
 * 对外公开，支持完整的代码高亮功能。
 *
 * @param code 代码字符串
 * @param language 语言标识符（如 "kotlin"、"python"）
 * @param modifier Compose Modifier
 * @param isStreaming 是否处于流式输出状态，为 true 时在末尾显示光标动画
 * @param theme 代码主题，默认使用 LocalCodeTheme
 * @param showLineNumbers 是否显示行号
 * @param showCopyButton 是否显示复制按钮
 * @param maxVisibleLines 最大可见行数，超出时显示折叠按钮（null 表示不限制）
 * @param onTokenClick Token 点击回调
 */
@Composable
fun CodeBlock(
    code: String,
    language: String = "",
    modifier: Modifier = Modifier,
    isStreaming: Boolean = false,
    theme: CodeTheme = LocalCodeTheme.current,
    showLineNumbers: Boolean = false,
    showCopyButton: Boolean = true,
    maxVisibleLines: Int? = null,
    onTokenClick: ((CodeToken) -> Unit)? = null
) {
    // 增量高亮引擎（按语言分组，语言变化时重置）
    val highlighter = remember(language) { IncrementalHighlighter() }

    // 增量解析代码
    val ast by remember(code, language) {
        derivedStateOf { highlighter.update(code, language) }
    }

    // 折叠状态
    var isExpanded by remember { mutableStateOf(false) }
    val lines = code.split("\n")
    val totalLines = lines.size
    val isCollapsible = maxVisibleLines != null && totalLines > maxVisibleLines
    val visibleLineCount = when {
        !isCollapsible || isExpanded -> totalLines
        else -> maxVisibleLines // isCollapsible 为 true 时 maxVisibleLines 一定非空
    }

    // 可见行列表
    val visibleLines = lines.take(visibleLineCount)

    // 逐行构建高亮 AnnotatedString（每行单独解析，保证行号与代码行严格对齐）
    val visibleCode = visibleLines.joinToString("\n")
    val visibleAst = remember(visibleCode, language) { highlighter.update(visibleCode, language) }
    val lineHighlights = remember(visibleAst, theme) {
        buildLineHighlights(visibleLines, visibleAst.tokens, theme)
    }

    Column(
        modifier = modifier
            .background(theme.background)
            .fillMaxWidth()
    ) {
        // 顶部工具栏（语言标签 + 复制按钮）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LanguageLabel(language = language, theme = theme)
            if (showCopyButton) {
                CopyButton(code = code, theme = theme)
            }
        }

        // 代码内容区域（逐行渲染，行号与代码行严格对齐）
        val horizontalScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                lineHighlights.forEachIndexed { index, lineText ->
                    val isLastLine = index == lineHighlights.lastIndex
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 行号
                        if (showLineNumbers) {
                            BasicText(
                                text = (index + 1).toString(),
                                style = TextStyle(
                                    color = theme.colorFor(com.hrm.codehigh.ast.TokenType.COMMENT).copy(alpha = 0.5f),
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = 20.sp
                                ),
                                modifier = Modifier
                                    .width(40.dp)
                                    .padding(end = 8.dp),
                                maxLines = 1
                            )
                            // 分隔线
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .fillMaxHeight()
                                    .background(
                                        theme.colorFor(com.hrm.codehigh.ast.TokenType.COMMENT)
                                            .copy(alpha = 0.3f)
                                    )
                            )
                        }

                        // 代码行文本
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BasicText(
                                text = lineText,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = 20.sp
                                )
                            )
                            // 流式光标仅在最后一行末尾显示
                            if (isLastLine) {
                                StreamingCursor(
                                    isStreaming = isStreaming,
                                    color = theme.colorFor(com.hrm.codehigh.ast.TokenType.PLAIN)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 折叠/展开按钮
        if (isCollapsible) {
            BasicText(
                text = if (isExpanded) "▲ 收起" else "▼ 展开 (${totalLines - visibleLineCount} 行)",
                style = TextStyle(
                    color = theme.colorFor(com.hrm.codehigh.ast.TokenType.FUNCTION),
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}

/**
 * 复制按钮组件。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 */
@Composable
internal fun CopyButton(
    code: String,
    theme: CodeTheme
) {
    @Suppress("DEPRECATION")
    val clipboardManager = LocalClipboardManager.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
            copied = false
        }
    }

    BasicText(
        text = if (copied) "✓ 已复制" else "复制",
        style = TextStyle(
            color = theme.colorFor(TokenType.FUNCTION).copy(alpha = 0.8f),
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
        ),
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                @Suppress("DEPRECATION")
                clipboardManager.setText(AnnotatedString(code))
                copied = true
            }
    )
}
