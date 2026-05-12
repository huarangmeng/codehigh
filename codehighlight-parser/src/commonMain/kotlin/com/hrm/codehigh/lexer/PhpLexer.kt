package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType

internal object PhpLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "abstract", "and", "array", "as", "break", "callable", "case", "catch", "class",
            "clone", "const", "continue", "declare", "default", "do", "echo", "else", "elseif",
            "empty", "enum", "exit", "extends", "false", "final", "finally", "fn", "for",
            "foreach", "function", "global", "if", "implements", "include", "include_once",
            "instanceof", "interface", "isset", "match", "namespace", "new", "null", "or",
            "print", "private", "protected", "public", "readonly", "require", "require_once",
            "return", "self", "static", "switch", "throw", "trait", "true", "try", "use",
            "var", "while", "xor", "yield"
        ),
        builtins = setOf("count", "json_encode", "json_decode", "array_merge", "implode", "explode", "sprintf"),
        types = setOf("int", "float", "string", "bool", "array", "object", "mixed", "callable", "iterable", "void"),
        fixedTokens = mapOf("<?php" to TokenType.KEYWORD, "<?=" to TokenType.KEYWORD, "?>" to TokenType.KEYWORD),
        lineComments = listOf("//", "#"),
        blockComments = listOf("/*" to "*/"),
        stringQuotes = setOf('"', '\''),
        variablePrefixes = listOf("$"),
        caseInsensitiveWords = true,
        uppercaseIdentifiersAreTypes = true,
        extraWordChars = setOf('\\'),
        operators = setOf("??=", "??", "=>", "->", "::", "===", "!==", "==", "!=", "<=", ">=", "&&", "||", "+=", "-=", "*=", "/=", ".=", "%=", "&=", "|=", "^=", "<<", ">>", "=", ".", "+", "-", "*", "/", "%", "!", "<", ">", "&", "|", "^", "?"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
