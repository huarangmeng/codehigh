package com.hrm.codehigh.preview.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock
import com.hrm.codehigh.theme.*

/**
 * 主题对比分类，展示同一段代码在 4 套内置主题下的对比效果。
 */
@Composable
internal fun ThemeCategory() {
    val themes = listOf(
        "One Dark Pro（暗色）" to OneDarkProTheme,
        "GitHub Light（亮色）" to GithubLightTheme,
        "Dracula Pro（暗色）" to DraculaProTheme,
        "Solarized Light（亮色）" to SolarizedLightTheme,
    )

    val sampleCode = SampleCode.KOTLIN

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(themes) { (name, theme) ->
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CodeBlock(
                    code = sampleCode,
                    language = "kotlin",
                    theme = theme,
                    showLineNumbers = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
