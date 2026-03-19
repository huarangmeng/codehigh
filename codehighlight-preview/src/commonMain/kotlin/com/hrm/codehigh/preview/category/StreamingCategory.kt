package com.hrm.codehigh.preview.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock
import kotlinx.coroutines.delay

/**
 * 流式演示分类，模拟逐字符流式输出效果。
 */
@Composable
internal fun StreamingCategory() {
    val fullCode = SampleCode.KOTLIN
    var streamingCode by remember { mutableStateOf("") }
    var isStreaming by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // 模拟流式输出
    LaunchedEffect(isStreaming) {
        if (isStreaming) {
            streamingCode = ""
            isCompleted = false
            for (char in fullCode) {
                streamingCode += char
                delay(20) // 每个字符延迟 20ms
            }
            isStreaming = false
            isCompleted = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "流式输出演示",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "模拟 AI 逐字符输出代码的效果，展示 isStreaming 参数的光标动画。",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { isStreaming = true },
                enabled = !isStreaming
            ) {
                Text(if (isStreaming) "输出中..." else "开始流式输出")
            }

            OutlinedButton(
                onClick = {
                    isStreaming = false
                    streamingCode = ""
                    isCompleted = false
                }
            ) {
                Text("重置")
            }
        }

        // 状态指示
        if (isCompleted) {
            Text(
                text = "✓ 输出完成",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // 代码块（流式模式）
        if (streamingCode.isNotEmpty() || isStreaming) {
            CodeBlock(
                code = streamingCode,
                language = "kotlin",
                isStreaming = isStreaming,
                showLineNumbers = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
