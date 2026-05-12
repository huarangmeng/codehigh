package com.hrm.codehigh.theme

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal，支持全局主题注入。
 * 默认使用 OneDarkProTheme 暗色主题。
 * 对外公开，供调用方注入自定义主题。
 */
val LocalCodeTheme = staticCompositionLocalOf<CodeTheme> {
    OneDarkProTheme
}
