package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object ScalaLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "abstract", "case", "catch", "class", "def", "do", "else", "enum", "export",
            "extends", "false", "final", "finally", "for", "forSome", "given", "if", "implicit",
            "import", "lazy", "match", "new", "null", "object", "override", "package", "private",
            "protected", "return", "sealed", "super", "then", "this", "throw", "trait", "true",
            "try", "type", "using", "val", "var", "while", "with", "yield"
        ),
        builtins = setOf("println", "Option", "Some", "None", "Future", "Seq", "Map", "List"),
        types = setOf("Int", "Long", "Double", "Float", "Boolean", "String", "Unit", "Any", "Nothing"),
        lineComments = listOf("//"),
        blockComments = listOf("/*" to "*/"),
        tripleStrings = setOf("\"\"\""),
        stringQuotes = setOf('"', '\''),
        annotationPrefix = '@',
        uppercaseIdentifiersAreTypes = true,
        operators = setOf("=>", "<-", "<:", ">:", "::", "#", "==", "!=", "<=", ">=", "&&", "||", "+=", "-=", "*=", "/=", "%=", "=", "+", "-", "*", "/", "%", "!", "<", ">", "&", "|", "^", ":", "@"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
