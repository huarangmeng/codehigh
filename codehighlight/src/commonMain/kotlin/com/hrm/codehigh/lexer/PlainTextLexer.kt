package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType

/**
 * 纯文本降级词法分析器，将整段文本作为单个 PLAIN Token 返回。
 * 标记为 internal，仅作降级兜底使用。
 * 未知语言或空语言标识时自动降级到此实现。
 */
internal object PlainTextLexer : Lexer {
    override fun tokenize(code: String): List<CodeToken> {
        if (code.isEmpty()) return emptyList()
        return listOf(CodeToken(TokenType.PLAIN, code, code.indices))
    }
}
