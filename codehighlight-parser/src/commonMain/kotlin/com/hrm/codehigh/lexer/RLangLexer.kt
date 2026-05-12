package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object RLangLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "if", "else", "repeat", "while", "function", "for", "in", "next", "break",
            "TRUE", "FALSE", "NULL", "NA", "NaN", "Inf"
        ),
        builtins = setOf(
            "library", "require", "data.frame", "ggplot", "print", "cat", "message", "c",
            "list", "matrix", "factor", "mean", "sum", "paste", "dplyr", "tibble"
        ),
        lineComments = listOf("#"),
        stringQuotes = setOf('"', '\''),
        extraWordStartChars = setOf('.'),
        extraWordChars = setOf('.'),
        operators = setOf("%in%", "%/%", "%%", "<<-", "<-", "->>", "->", "<=", ">=", "==", "!=", "&&", "||", "=", "+", "-", "*", "/", "%", "!", "<", ">", "&", "|", ":"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
