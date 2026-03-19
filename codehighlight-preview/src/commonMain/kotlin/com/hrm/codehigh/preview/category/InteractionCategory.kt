package com.hrm.codehigh.preview.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock

/**
 * 交互功能分类，演示复制按钮、行号切换、代码折叠、Token 点击等功能。
 */
@Composable
internal fun InteractionCategory() {
    var showLineNumbers by remember { mutableStateOf(true) }
    var showCopyButton by remember { mutableStateOf(true) }
    var maxVisibleLines by remember { mutableStateOf<Int?>(10) }
    var lastClickedToken by remember { mutableStateOf<CodeToken?>(null) }

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
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = showLineNumbers,
                            onCheckedChange = { showLineNumbers = it }
                        )
                        Text("显示行号")
                    }

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = showCopyButton,
                            onCheckedChange = { showCopyButton = it }
                        )
                        Text("显示复制按钮")
                    }

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
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
    }
}
