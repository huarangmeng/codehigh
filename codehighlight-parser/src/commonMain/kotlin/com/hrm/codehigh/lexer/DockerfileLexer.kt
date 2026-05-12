package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

internal object DockerfileLexer : BaseLexer() {
    private val spec = ConfigurableLexerSpec(
        keywords = setOf(
            "FROM", "RUN", "CMD", "LABEL", "EXPOSE", "ENV", "ADD", "COPY", "ENTRYPOINT",
            "VOLUME", "USER", "WORKDIR", "ARG", "ONBUILD", "STOPSIGNAL", "HEALTHCHECK",
            "SHELL", "MAINTAINER"
        ),
        builtins = setOf("AS"),
        lineComments = listOf("#"),
        stringQuotes = setOf('"', '\''),
        variablePrefixes = listOf("$"),
        caseInsensitiveWords = true,
        extraWordChars = setOf('-', '.'),
        operators = setOf("&&", "||", "<<", "=", "\\"),
        punctuation = setOf('{', '}', '(', ')', '[', ']', ';', ',', '.', ':')
    )

    override fun tokenize(code: String): List<CodeToken> = tokenizeWithSpec(code, spec)
}
