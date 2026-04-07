package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object RubyLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "BEGIN", "END", "alias", "and", "begin", "break", "case", "class", "def", "defined?",
            "do", "else", "elsif", "end", "ensure", "false", "for", "if", "in", "module", "next",
            "nil", "not", "or", "redo", "rescue", "retry", "return", "self", "super", "then",
            "true", "undef", "unless", "until", "when", "while", "yield"
        ),
        builtins = setOf(
            "puts", "print", "p", "require", "require_relative", "include", "extend",
            "attr_accessor", "attr_reader", "attr_writer", "raise", "lambda", "proc"
        ),
        lineComments = listOf("#"),
        blockComments = listOf("=begin" to "=end"),
        stringQuotes = setOf('"', '\''),
        variablePrefixes = listOf("@@", "@", "$"),
        uppercaseIdentifiersAreTypes = true,
        extraWordChars = setOf('?', '!'),
        operators = setOf("===", "==", "!=", "<=", ">=", "<=>", "&&", "||", "::", "=>", "..", "...", "+=", "-=", "*=", "/=", "%=", "**", "||=", "&&=", "&.", "&", "|", "!", "<", ">", "=", "+", "-", "*", "/", "%"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
