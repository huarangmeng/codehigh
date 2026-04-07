package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object LuaLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "and", "break", "do", "else", "elseif", "end", "false", "for", "function",
            "goto", "if", "in", "local", "nil", "not", "or", "repeat", "return",
            "then", "true", "until", "while"
        ),
        builtins = setOf(
            "require", "print", "pairs", "ipairs", "next", "tonumber", "tostring",
            "table", "string", "math", "coroutine"
        ),
        lineComments = listOf("--"),
        blockComments = listOf("--[[" to "]]"),
        blockStrings = listOf("[[" to "]]"),
        stringQuotes = setOf('"', '\''),
        extraWordChars = setOf('.'),
        operators = setOf("==", "~=", "<=", ">=", "..", "...", "=", "+", "-", "*", "/", "%", "<", ">", "#"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
