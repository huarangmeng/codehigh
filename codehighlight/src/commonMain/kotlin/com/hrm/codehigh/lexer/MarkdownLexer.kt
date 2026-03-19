package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType

/**
 * Markdown 词法分析器。
 * 标记为 internal，外部通过 LanguageRegistry 访问。
 */
internal object MarkdownLexer : BaseLexer() {

    override fun tokenize(code: String): List<CodeToken> {
        if (code.isEmpty()) return emptyList()
        val tokens = mutableListOf<CodeToken>()
        val lines = code.split("\n")
        var pos = 0

        for (line in lines) {
            val lineEnd = pos + line.length
            var linePos = pos

            when {
                // ATX 标题 # ## ###
                line.startsWith("#") -> {
                    var hashEnd = linePos
                    while (hashEnd < lineEnd && code[hashEnd] == '#') hashEnd++
                    tokens.add(CodeToken(TokenType.KEYWORD, code.substring(linePos, hashEnd), linePos until hashEnd))
                    if (hashEnd < lineEnd) {
                        tokens.add(CodeToken(TokenType.FUNCTION, code.substring(hashEnd, lineEnd), hashEnd until lineEnd))
                    }
                }

                // 代码围栏 ```
                line.startsWith("```") || line.startsWith("~~~") -> {
                    tokens.add(CodeToken(TokenType.PUNCTUATION, code.substring(linePos, lineEnd), linePos until lineEnd))
                }

                // 引用块 >
                line.startsWith(">") -> {
                    tokens.add(CodeToken(TokenType.COMMENT, code.substring(linePos, lineEnd), linePos until lineEnd))
                }

                // 分隔线 --- *** ___
                line.matches(Regex("^[-*_]{3,}\\s*$")) -> {
                    tokens.add(CodeToken(TokenType.OPERATOR, code.substring(linePos, lineEnd), linePos until lineEnd))
                }

                // 列表标记 - * + 1.
                line.matches(Regex("^\\s*[-*+]\\s.*")) || line.matches(Regex("^\\s*\\d+\\.\\s.*")) -> {
                    val markerEnd = line.indexOf(' ', line.indexOfFirst { !it.isWhitespace() }) + linePos + 1
                    tokens.add(CodeToken(TokenType.KEYWORD, code.substring(linePos, markerEnd), linePos until markerEnd))
                    if (markerEnd < lineEnd) {
                        tokenizeInlineMarkdown(code, markerEnd, lineEnd, tokens)
                    }
                }

                else -> {
                    tokenizeInlineMarkdown(code, linePos, lineEnd, tokens)
                }
            }

            pos = lineEnd
            // 添加换行符
            if (pos < code.length && code[pos] == '\n') {
                tokens.add(CodeToken(TokenType.PLAIN, "\n", pos until pos + 1))
                pos++
            }
        }

        return tokens
    }

    /**
     * 处理行内 Markdown 语法（粗体、斜体、行内代码、链接等）。
     */
    private fun tokenizeInlineMarkdown(code: String, start: Int, end: Int, tokens: MutableList<CodeToken>) {
        var pos = start

        while (pos < end) {
            val c = code[pos]

            // 行内代码 `code`
            if (c == '`') {
                val codeStart = pos
                pos++
                while (pos < end && code[pos] != '`') pos++
                if (pos < end) pos++
                tokens.add(CodeToken(TokenType.STRING, code.substring(codeStart, pos), codeStart until pos))
                continue
            }

            // 粗体 **text** 或 __text__
            if ((c == '*' && pos + 1 < end && code[pos + 1] == '*') ||
                (c == '_' && pos + 1 < end && code[pos + 1] == '_')) {
                val boldStart = pos
                val marker = code.substring(pos, pos + 2)
                pos += 2
                while (pos + 1 < end && !code.startsWith(marker, pos)) pos++
                if (pos + 1 < end) pos += 2
                tokens.add(CodeToken(TokenType.KEYWORD, code.substring(boldStart, pos), boldStart until pos))
                continue
            }

            // 斜体 *text* 或 _text_
            if (c == '*' || c == '_') {
                val italicStart = pos
                val marker = c
                pos++
                while (pos < end && code[pos] != marker) pos++
                if (pos < end) pos++
                tokens.add(CodeToken(TokenType.COMMENT, code.substring(italicStart, pos), italicStart until pos))
                continue
            }

            // 链接 [text](url) 或图片 ![alt](url)
            if (c == '!' && pos + 1 < end && code[pos + 1] == '[') {
                val linkStart = pos
                pos += 2
                while (pos < end && code[pos] != ']') pos++
                if (pos < end) pos++
                if (pos < end && code[pos] == '(') {
                    pos++
                    while (pos < end && code[pos] != ')') pos++
                    if (pos < end) pos++
                }
                tokens.add(CodeToken(TokenType.FUNCTION, code.substring(linkStart, pos), linkStart until pos))
                continue
            }

            if (c == '[') {
                val linkStart = pos
                pos++
                while (pos < end && code[pos] != ']') pos++
                if (pos < end) pos++
                if (pos < end && code[pos] == '(') {
                    pos++
                    while (pos < end && code[pos] != ')') pos++
                    if (pos < end) pos++
                }
                tokens.add(CodeToken(TokenType.FUNCTION, code.substring(linkStart, pos), linkStart until pos))
                continue
            }

            // 普通文本
            tokens.add(CodeToken(TokenType.PLAIN, c.toString(), pos until pos + 1))
            pos++
        }
    }
}
