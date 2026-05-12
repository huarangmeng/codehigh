package com.hrm.codehigh.theme

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType

/**
 * Solarized Light 亮色主题，基于 Solarized Light 配色方案。
 */
object SolarizedLightTheme : CodeTheme {
    override val background: Color = Color(0xFFFDF6E3)
    override val isDark: Boolean = false

    override fun colorFor(type: TokenType): Color = when (type) {
        TokenType.KEYWORD -> Color(0xFF859900) // 绿色
        TokenType.STRING -> Color(0xFF2AA198) // 青色
        TokenType.NUMBER -> Color(0xFFD33682) // 洋红
        TokenType.COMMENT -> Color(0xFF93A1A1) // 浅灰
        TokenType.OPERATOR -> Color(0xFF657B83) // 深灰
        TokenType.PUNCTUATION -> Color(0xFF657B83) // 深灰
        TokenType.IDENTIFIER -> Color(0xFF657B83) // 深灰
        TokenType.TYPE -> Color(0xFFCB4B16) // 橙红
        TokenType.FUNCTION -> Color(0xFF268BD2) // 蓝色
        TokenType.VARIABLE -> Color(0xFF657B83) // 深灰
        TokenType.CONSTANT -> Color(0xFFD33682) // 洋红
        TokenType.ANNOTATION -> Color(0xFFCB4B16) // 橙红
        TokenType.DECORATOR -> Color(0xFFCB4B16) // 橙红
        TokenType.BUILTIN -> Color(0xFF268BD2) // 蓝色
        TokenType.PLAIN -> Color(0xFF657B83) // 深灰
    }
}
