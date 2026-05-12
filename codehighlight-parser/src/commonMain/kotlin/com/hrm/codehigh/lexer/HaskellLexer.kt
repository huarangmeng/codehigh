package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object HaskellLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "case", "class", "data", "default", "deriving", "do", "else", "if", "import",
            "in", "infix", "infixl", "infixr", "instance", "let", "module", "newtype",
            "of", "then", "type", "where"
        ),
        builtins = setOf("print", "putStrLn", "map", "foldl", "foldr", "Maybe", "Either", "Just", "Nothing"),
        lineComments = listOf("--"),
        blockComments = listOf("{-" to "-}"),
        stringQuotes = setOf('"', '\''),
        uppercaseIdentifiersAreTypes = true,
        extraWordChars = setOf('\''),
        operators = setOf("::", "->", "=>", "<-", "..", "==", "/=", "<=", ">=", "&&", "||", "=", "+", "-", "*", "/", "%", "<", ">", "|", "\\"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
