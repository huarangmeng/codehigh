package com.hrm.codehigh.theme

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType

/**
 * GitHub Light 亮色主题，基于 GitHub 代码高亮配色方案。
 */
object GithubLightTheme : CodeTheme {
    override val background: Color = Color(0xFFFFFFFF)
    override val isDark: Boolean = false

    override fun colorFor(type: TokenType): Color = when (type) {
        TokenType.KEYWORD -> Color(0xFFCF222E) // 红色
        TokenType.STRING -> Color(0xFF0A3069) // 深蓝
        TokenType.NUMBER -> Color(0xFF0550AE) // 蓝色
        TokenType.COMMENT -> Color(0xFF6E7781) // 灰色
        TokenType.OPERATOR -> Color(0xFF24292F) // 深色
        TokenType.PUNCTUATION -> Color(0xFF24292F) // 深色
        TokenType.IDENTIFIER -> Color(0xFF24292F) // 深色
        TokenType.TYPE -> Color(0xFF953800) // 棕色
        TokenType.FUNCTION -> Color(0xFF8250DF) // 紫色
        TokenType.VARIABLE -> Color(0xFF24292F) // 深色
        TokenType.CONSTANT -> Color(0xFF0550AE) // 蓝色
        TokenType.ANNOTATION -> Color(0xFF953800) // 棕色
        TokenType.DECORATOR -> Color(0xFF953800) // 棕色
        TokenType.BUILTIN -> Color(0xFF0550AE) // 蓝色
        TokenType.PLAIN -> Color(0xFF24292F) // 深色
    }
}
