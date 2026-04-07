package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExtendedLanguageSupportTest {

    @Test
    fun should_resolveAliases_when_extendedLanguagesAreRequested() {
        val aliases = mapOf(
            "rb" to "ruby",
            "phtml" to "php",
            "sc" to "scala",
            "rscript" to "r",
            "docker" to "dockerfile",
            "hs" to "haskell",
            "exs" to "elixir",
            "patch" to "diff"
        )

        aliases.forEach { (alias, canonical) ->
            assertEquals(LanguageRegistry.get(canonical), LanguageRegistry.get(alias))
        }
    }

    @Test
    fun should_tokenizeRuby_when_commonRubySyntaxPresent() {
        val tokens = LanguageRegistry.getOrPlain("ruby").tokenize(
            "class User\n  attr_accessor :name\n  def greet\n    puts \"hi\"\n  end\nend"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "class" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "attr_accessor" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "puts" })
    }

    @Test
    fun should_tokenizePhp_when_variablesAndTagsPresent() {
        val tokens = LanguageRegistry.getOrPlain("php").tokenize(
            "<?php\nfunction hello(\$name) {\n    echo \$name;\n}"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "<?php" })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text.equals("function", ignoreCase = true) })
        assertTrue(tokens.any { it.type == TokenType.VARIABLE && it.text == "\$name" })
    }

    @Test
    fun should_tokenizeDart_when_flutterSyntaxPresent() {
        val tokens = LanguageRegistry.getOrPlain("dart").tokenize(
            "@override\nWidget build(BuildContext context) {\n  setState(() {});\n}"
        )

        assertTrue(tokens.any { it.type == TokenType.ANNOTATION && it.text == "@override" })
        assertTrue(tokens.any { it.type == TokenType.TYPE && it.text == "Widget" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "setState" })
    }

    @Test
    fun should_tokenizeScala_when_caseClassSyntaxPresent() {
        val tokens = LanguageRegistry.getOrPlain("scala").tokenize(
            "case class User(name: String)\ntrait Greeter\nval count = 1"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "case" })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "trait" })
        assertTrue(tokens.any { it.type == TokenType.TYPE && it.text == "User" })
    }

    @Test
    fun should_tokenizeR_when_assignmentAndBuiltinsPresent() {
        val tokens = LanguageRegistry.getOrPlain("r").tokenize(
            "users <- data.frame(name = c(\"Alice\"))\nlibrary(ggplot2)"
        )

        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "<-" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "data.frame" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "library" })
    }

    @Test
    fun should_tokenizeToml_when_sectionsAndValuesPresent() {
        val tokens = LanguageRegistry.getOrPlain("toml").tokenize(
            "[server]\nhost = \"127.0.0.1\"\nenabled = true"
        )

        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "=" })
        assertTrue(tokens.any { it.type == TokenType.STRING && it.text == "\"127.0.0.1\"" })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "true" })
    }

    @Test
    fun should_tokenizeDockerfile_when_instructionsPresent() {
        val tokens = LanguageRegistry.getOrPlain("dockerfile").tokenize(
            "FROM eclipse-temurin:21\nWORKDIR /app\nENV PORT=8080"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text.equals("FROM", ignoreCase = true) })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text.equals("WORKDIR", ignoreCase = true) })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text.equals("ENV", ignoreCase = true) })
    }

    @Test
    fun should_tokenizeLua_when_functionsPresent() {
        val tokens = LanguageRegistry.getOrPlain("lua").tokenize(
            "local function greet(name)\n  print(require(\"game.config\"))\nend"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "local" })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "function" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "print" })
    }

    @Test
    fun should_tokenizeHaskell_when_typeSignaturesPresent() {
        val tokens = LanguageRegistry.getOrPlain("haskell").tokenize(
            "module Main where\n\ndata User = User String Int\ngreet :: User -> String"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "module" })
        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "data" })
        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "::" })
    }

    @Test
    fun should_tokenizeElixir_when_pipelineSyntaxPresent() {
        val tokens = LanguageRegistry.getOrPlain("elixir").tokenize(
            "defmodule User do\n  \" Alice \" |> String.trim() |> IO.puts()\nend"
        )

        assertTrue(tokens.any { it.type == TokenType.KEYWORD && it.text == "defmodule" })
        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "|>" })
        assertTrue(tokens.any { it.type == TokenType.BUILTIN && it.text == "IO.puts" })
    }

    @Test
    fun should_returnLexer_when_extendedLanguageRequested() {
        val languages = listOf("ruby", "php", "dart", "scala", "r", "toml", "dockerfile", "lua", "haskell", "elixir", "diff")
        languages.forEach { language ->
            assertNotNull(LanguageRegistry.get(language))
        }
    }

    @Test
    fun should_tokenizeDiff_when_patchContentPresent() {
        val tokens = LanguageRegistry.getOrPlain("diff").tokenize(
            "diff --git a/file.kt b/file.kt\n@@ -1,2 +1,2 @@\n-old line\n+new line"
        )

        assertTrue(tokens.any { it.type == TokenType.ANNOTATION && it.text.startsWith("diff --git") })
        assertTrue(tokens.any { it.type == TokenType.FUNCTION && it.text.startsWith("@@") })
        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "-" })
        assertTrue(tokens.any { it.type == TokenType.OPERATOR && it.text == "+" })
    }
}
