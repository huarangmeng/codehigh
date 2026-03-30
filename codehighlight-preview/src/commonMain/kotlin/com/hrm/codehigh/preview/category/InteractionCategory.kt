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
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock
import com.hrm.codehigh.renderer.InlineCode
import com.hrm.codehigh.renderer.measureInlineCodeSize
import com.hrm.codehigh.theme.LocalCodeTheme

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
                showLineNumbers = showLineNumbers,
                showCopyButton = showCopyButton,
                maxVisibleLines = maxVisibleLines,
                onTokenClick = { token -> lastClickedToken = token },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 行内代码尺寸测量示例
        item {
            val inlineCodeText = remember { "val answer = 42" }
            val inlineCodeSize = remember(inlineCodeText, theme) {
                measureInlineCodeSize(
                    text = inlineCodeText,
                    language = "kotlin",
                    theme = theme,
                    density = density,
                    textMeasurer = textMeasurer
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("行内代码尺寸测量示例", style = MaterialTheme.typography.titleSmall)
                    
                    Text("以下展示了如何在渲染前预先测量行内代码的尺寸：")
                    
                    // 显示测量结果
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("测量结果：", style = MaterialTheme.typography.labelMedium)
                            Text("代码: ")
                            InlineCode(text = inlineCodeText)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("宽度: ${inlineCodeSize.width}px (${"%.2f".format(inlineCodeSize.widthDp(density))}dp)")
                            Text("高度: ${inlineCodeSize.height}px (${"%.2f".format(inlineCodeSize.heightDp(density))}dp)")
                        }
                    }
                    
                    // 使用测量结果进行占位
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
