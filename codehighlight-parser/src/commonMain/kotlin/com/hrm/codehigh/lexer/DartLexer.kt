package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object DartLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "abstract", "as", "assert", "async", "await", "break", "case", "catch", "class",
            "const", "continue", "covariant", "default", "deferred", "do", "dynamic", "else",
            "enum", "export", "extends", "extension", "external", "factory", "false", "final",
            "finally", "for", "function", "get", "hide", "if", "implements", "import", "in",
            "interface", "is", "late", "library", "mixin", "new", "null", "on", "operator",
            "part", "required", "rethrow", "return", "set", "show", "static", "super", "switch",
            "sync", "this", "throw", "true", "try", "typedef", "var", "void", "while", "with", "yield"
        ),
        builtins = setOf("runApp", "setState", "print"),
        types = setOf(
            "int", "double", "num", "bool", "String", "List", "Map", "Set", "Future",
            "Stream", "Widget", "State", "BuildContext", "StatelessWidget", "StatefulWidget",
            "Object", "dynamic", "Never", "Duration"
        ),
        lineComments = listOf("//"),
        blockComments = listOf("/*" to "*/"),
        tripleStrings = setOf("\"\"\"", "'''"),
        stringQuotes = setOf('"', '\''),
        annotationPrefix = '@',
        uppercaseIdentifiersAreTypes = true,
        operators = setOf("=>", "??=", "??", "..", "...", "==", "!=", "<=", ">=", "&&", "||", "<<", ">>", "+=", "-=", "*=", "/=", "%=", "~/", "++", "--", "=", "+", "-", "*", "/", "%", "!", "<", ">", "&", "|", "^", "?", ":"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
