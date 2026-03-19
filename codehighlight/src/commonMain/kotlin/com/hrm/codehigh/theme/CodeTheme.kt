package com.hrm.codehigh.theme

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType

/**
 * 代码主题接口，定义代码高亮的颜色方案。
 * 对外公开，支持自定义主题实现。
 */
interface CodeTheme {
    /** 按 Token 类型返回对应颜色 */
    fun colorFor(type: TokenType): Color

    /** 代码块背景色 */
    val background: Color

    /** 是否为暗色主题 */
    val isDark: Boolean
}

/**
 * 安全获取颜色，缺失类型时回退到 PLAIN 颜色。
 * 标记为 internal，仅供模块内部使用。
 */
internal fun CodeTheme.safeColorFor(type: TokenType): Color {
    return try {
        colorFor(type)
    } catch (e: Exception) {
        colorFor(TokenType.PLAIN)
    }
}
