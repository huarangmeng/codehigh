package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType

internal object DiffLexer : BaseLexer() {
    override fun tokenize(code: String): List<CodeToken> {
        if (code.isEmpty()) return emptyList()

        val tokens = mutableListOf<CodeToken>()
        var offset = 0
        val lines = code.split("\n")

        lines.forEachIndexed { index, line ->
            val lineLength = line.length
            val rangeEnd = offset + lineLength

            if (line.isNotEmpty()) {
                when {
                    line.startsWith("diff ") || line.startsWith("index ") -> {
                        tokens.add(CodeToken(TokenType.ANNOTATION, line, offset until rangeEnd))
                    }
                    line.startsWith("@@") -> {
                        tokens.add(CodeToken(TokenType.FUNCTION, line, offset until rangeEnd))
                    }
                    line.startsWith("+++") || line.startsWith("---") -> {
                        tokens.add(CodeToken(TokenType.TYPE, line, offset until rangeEnd))
                    }
                    line.startsWith("+") || line.startsWith("-") -> {
                        tokens.add(CodeToken(TokenType.OPERATOR, line.substring(0, 1), offset until offset + 1))
                        if (lineLength > 1) {
                            tokens.add(CodeToken(TokenType.PLAIN, line.substring(1), offset + 1 until rangeEnd))
                        }
                    }
                    else -> {
                        tokens.add(CodeToken(TokenType.PLAIN, line, offset until rangeEnd))
                    }
                }
            }

            if (index < lines.lastIndex) {
                tokens.add(CodeToken(TokenType.PLAIN, "\n", rangeEnd until rangeEnd + 1))
                offset = rangeEnd + 1
            } else {
                offset = rangeEnd
            }
        }

        return tokens
    }
}
