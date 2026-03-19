package com.hrm.codehigh.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hrm.codehigh.preview.category.InteractionCategory
import com.hrm.codehigh.preview.category.LanguageCategory
import com.hrm.codehigh.preview.category.StreamingCategory
import com.hrm.codehigh.preview.category.ThemeCategory

/**
 * 预览应用根组件，上下布局。
 * 上方：对应分类下的预览条目列表
 * 下方：4 个分类 Tab（语言高亮 / 主题对比 / 流式演示 / 交互功能）
 */
@Composable
fun CodeHighPreviewApp() {
    var selectedCategory by remember { mutableStateOf(PreviewCategory.LANGUAGE) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 上方内容区域
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (selectedCategory) {
                PreviewCategory.LANGUAGE -> LanguageCategory()
                PreviewCategory.THEME -> ThemeCategory()
                PreviewCategory.STREAMING -> StreamingCategory()
                PreviewCategory.INTERACTION -> InteractionCategory()
            }
        }

        // 底部导航栏
        NavigationBar {
            PreviewCategory.entries.forEach { category ->
                NavigationBarItem(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    icon = { Text(category.icon) },
                    label = { Text(category.label) }
                )
            }
        }
    }
}

/**
 * 预览分类枚举。
 */
internal enum class PreviewCategory(val label: String, val icon: String) {
    LANGUAGE("语言高亮", "🔤"),
    THEME("主题对比", "🎨"),
    STREAMING("流式演示", "⚡"),
    INTERACTION("交互功能", "🖱️")
}
