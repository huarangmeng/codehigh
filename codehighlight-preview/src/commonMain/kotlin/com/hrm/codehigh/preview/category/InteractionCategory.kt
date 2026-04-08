package com.hrm.codehigh.preview.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock
import com.hrm.codehigh.renderer.InlineCode
import com.hrm.codehigh.renderer.InlineCodeDefaults
import com.hrm.codehigh.renderer.measureInlineCodeSize
import com.hrm.codehigh.theme.LocalCodeTheme
import kotlin.math.round

/**
 * 交互功能分类，演示复制按钮、行号切换、代码折叠、Token 点击等功能。
 */
@Composable
internal fun InteractionCategory() {
    var showLineNumbers by remember { mutableStateOf(true) }
    var showCopyButton by remember { mutableStateOf(true) }
    var maxVisibleLines by remember { mutableStateOf<Int?>(10) }
    var lastClickedToken by remember { mutableStateOf<CodeToken?>(null) }
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val theme = LocalCodeTheme.current
    val inlineCodeStyle = remember(theme) { InlineCodeDefaults.style(theme) }
    val customInlineCodeStyle = remember(theme) {
        inlineCodeStyle.copy(
            textStyle = inlineCodeStyle.textStyle.copy(
                fontSize = 14.sp,
            ),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "交互功能演示",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // 控制面板
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("控制面板", style = MaterialTheme.typography.titleSmall)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = showLineNumbers,
                            onCheckedChange = { showLineNumbers = it }
                        )
                        Text("显示行号")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = showCopyButton,
                            onCheckedChange = { showCopyButton = it }
                        )
                        Text("显示复制按钮")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = maxVisibleLines != null,
                            onCheckedChange = { maxVisibleLines = if (it) 10 else null }
                        )
                        Text("代码折叠（最多显示 10 行）")
                    }
                }
            }
        }

        // Token 点击信息
        item {
            if (lastClickedToken != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("最后点击的 Token：", style = MaterialTheme.typography.labelMedium)
                        Text("类型: ${lastClickedToken!!.type}")
                        Text("文本: \"${lastClickedToken!!.text}\"")
                        Text("位置: ${lastClickedToken!!.range}")
                    }
                }
            }
        }

        // 代码块演示
        item {
            Text("代码块演示", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            CodeBlock(
                code = SampleCode.KOTLIN,
                language = "kotlin",
                title = "CodeHighDemo.kt",
                showLineNumbers = showLineNumbers,
                showCopyButton = showCopyButton,
                maxVisibleLines = maxVisibleLines,
                onTokenClick = { token -> lastClickedToken = token },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text("标题、行高亮与起始行号", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            CodeBlock(
                code = SampleCode.KOTLIN,
                language = "kotlin",
                title = "feature/highlight-title.kt",
                showLineNumbers = true,
                startLine = 100,
                highlightedLines = setOf(2, 4, 5),
                showCopyButton = showCopyButton,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text("Diff 模式", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            CodeBlock(
                code = SampleCode.DIFF,
                language = "diff",
                showLineNumbers = true,
                showCopyButton = showCopyButton,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text("行内代码默认样式", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("默认样式现在试用你给的这套配色：日间模式是 #F5F7FA 底、#2E3440 字，夜间模式是 #2A2F3A 底、#ECEFF4 字，并补上一圈 1dp 细边框。")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("例如")
                        InlineCode(text = "README.md")
                        Text("和")
                        InlineCode(text = "draft")
                        Text("会更接近中性色标签，观感更干净，也更容易融入正文。")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("命令行片段")
                        InlineCode(text = "git status")
                        Text("、")
                        InlineCode(text = "git add .")
                        Text("与")
                        InlineCode(text = "git commit")
                        Text("现在会更偏克制、清爽的默认风格，而不是带明显情绪色的标签。")
                    }
                    Text("也可以继续通过一个 style 统一控制字号、留白与测量结果：")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("比如")
                        InlineCode(text = "notes", style = customInlineCodeStyle)
                        Text("与")
                        InlineCode(text = "todo", style = customInlineCodeStyle)
                        Text("可以共享一套外部样式。")
                    }
                }
            }
        }

        item {
            val inlineCodeText = remember { "README.md" }
            val inlineCodeSize = remember(inlineCodeText, customInlineCodeStyle) {
                measureInlineCodeSize(
                    text = inlineCodeText,
                    language = "kotlin",
                    style = customInlineCodeStyle,
                    density = density,
                    textMeasurer = textMeasurer,
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("行内代码尺寸测量示例", style = MaterialTheme.typography.titleSmall)
                    Text("以下展示了如何在渲染前预先测量行内代码的尺寸，并直接复用同一个 style：")
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("测量结果：", style = MaterialTheme.typography.labelMedium)
                            Text("代码: ")
                            InlineCode(text = inlineCodeText, style = customInlineCodeStyle)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("宽度: ${inlineCodeSize.width}px (${formatDpValue(inlineCodeSize.widthDp(density))}dp)")
                            Text("高度: ${inlineCodeSize.height}px (${formatDpValue(inlineCodeSize.heightDp(density))}dp)")
                        }
                    }
                    Text("使用测量结果占位的示例：")
                    Box(
                        modifier = Modifier
                            .width(inlineCodeSize.widthDp(density).dp)
                            .height(inlineCodeSize.heightDp(density).dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("占位区域", style = MaterialTheme.typography.bodySmall)
                    }
                    Text("占位区域尺寸与实际行内代码尺寸完全一致！")
                }
            }
        }
    }
}

private fun formatDpValue(value: Float): String = (round(value * 100f) / 100f).toString()
