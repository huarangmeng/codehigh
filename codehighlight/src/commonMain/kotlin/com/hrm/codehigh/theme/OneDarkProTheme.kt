package com.hrm.codehigh.theme

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType

/**
 * One Dark Pro 暗色主题，基于 Atom One Dark Pro 配色方案。
 */
object OneDarkProTheme : CodeTheme {
    override val background: Color = Color(0xFF282C34)
    override val isDark: Boolean = true

    override fun colorFor(type: TokenType): Color = when (type) {
        TokenType.KEYWORD -> Color(0xFFC678DD) // 紫色
        TokenType.STRING -> Color(0xFF98C379) // 绿色
        TokenType.NUMBER -> Color(0xFFD19A66) // 橙色
        TokenType.COMMENT -> Color(0xFF5C6370) // 灰色（斜体）
        TokenType.OPERATOR -> Color(0xFF56B6C2) // 青色
        TokenType.PUNCTUATION -> Color(0xFFABB2BF) // 浅灰
        TokenType.IDENTIFIER -> Color(0xFFABB2BF) // 浅灰
        TokenType.TYPE -> Color(0xFFE5C07B) // 黄色
        TokenType.FUNCTION -> Color(0xFF61AFEF) // 蓝色
        TokenType.VARIABLE -> Color(0xFFE06C75) // 红色
        TokenType.CONSTANT -> Color(0xFFD19A66) // 橙色
        TokenType.ANNOTATION -> Color(0xFFE5C07B) // 黄色
        TokenType.DECORATOR -> Color(0xFFE5C07B) // 黄色
        TokenType.BUILTIN -> Color(0xFF56B6C2) // 青色
        TokenType.PLAIN -> Color(0xFFABB2BF) // 浅灰
    }
}
