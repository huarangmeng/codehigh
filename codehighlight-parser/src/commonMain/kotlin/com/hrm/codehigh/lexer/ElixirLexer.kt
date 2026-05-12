package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object ElixirLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "after", "alias", "case", "catch", "cond", "def", "defmodule", "defp", "defstruct",
            "do", "else", "end", "false", "fn", "for", "if", "import", "in", "nil", "quote",
            "raise", "receive", "require", "rescue", "super", "throw", "true", "try", "unless",
            "unquote", "use", "when", "with"
        ),
        builtins = setOf("IO.puts", "IO.inspect", "Enum.map", "Enum.each", "String.trim"),
        lineComments = listOf("#"),
        tripleStrings = setOf("\"\"\"", "'''"),
        stringQuotes = setOf('"', '\''),
        uppercaseIdentifiersAreTypes = true,
        extraWordChars = setOf('.', '?', '!'),
        operators = setOf("|>", "<-", "=>", "::", "==", "!=", "<=", ">=", "&&", "||", "++", "--", "\\\\", "=", "+", "-", "*", "/", "%", "<", ">", "&", "|", "!"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
