package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType

/**
 * JSON 词法分析器。
 * 标记为 internal，外部通过 LanguageRegistry 访问。
 */
internal object JsonLexer : BaseLexer() {

    override fun tokenize(code: String): List<CodeToken> {
        if (code.isEmpty()) return emptyList()
        val tokens = mutableListOf<CodeToken>()
        var pos = 0

        while (pos < code.length) {
            val c = code[pos]

            // 字符串（键或值）
            if (c == '"') {
                val start = pos
                pos++
                while (pos < code.length && code[pos] != '"') {
                    if (code[pos] == '\\') pos++
                    pos++
                }
                if (pos < code.length && code[pos] == '"') pos++
                val text = code.substring(start, pos)
                // 判断是键还是值：跳过空白后看是否有冒号
                var lookAhead = pos
                while (lookAhead < code.length && code[lookAhead].isWhitespace()) lookAhead++
                val isKey = lookAhead < code.length && code[lookAhead] == ':'
                tokens.add(CodeToken(if (isKey) TokenType.KEYWORD else TokenType.STRING, text, start until pos))
                continue
            }

            // 数字
            if (c.isDigit() || (c == '-' && pos + 1 < code.length && code[pos + 1].isDigit())) {
                val start = pos
                if (c == '-') pos++
                while (pos < code.length && code[pos].isDigit()) pos++
                if (pos < code.length && code[pos] == '.') {
                    pos++
                    while (pos < code.length && code[pos].isDigit()) pos++
                }
                if (pos < code.length && (code[pos] == 'e' || code[pos] == 'E')) {
                    pos++
                    if (pos < code.length && (code[pos] == '+' || code[pos] == '-')) pos++
                    while (pos < code.length && code[pos].isDigit()) pos++
                }
                tokens.add(CodeToken(TokenType.NUMBER, code.substring(start, pos), start until pos))
                continue
            }

            // 布尔值和 null
            if (c.isLetter()) {
                val start = pos
                while (pos < code.length && code[pos].isLetter()) pos++
                val word = code.substring(start, pos)
                val type = when (word) {
                    "true", "false", "null" -> TokenType.BUILTIN
                    else -> TokenType.IDENTIFIER
                }
                tokens.add(CodeToken(type, word, start until pos))
                continue
            }

            // 结构符号
            if (c in "{}[],:") {
                tokens.add(CodeToken(TokenType.PUNCTUATION, c.toString(), pos until pos + 1))
                pos++
                continue
            }

            // 其他字符（空白等）
            tokens.add(CodeToken(TokenType.PLAIN, c.toString(), pos until pos + 1))
            pos++
        }

        return tokens
    }
}
