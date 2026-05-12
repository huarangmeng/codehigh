package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KotlinLexerTest {

    @Test
    fun should_returnEmptyList_when_emptyInput() {
        val tokens = KotlinLexer.tokenize("")
        assertTrue(tokens.isEmpty())
    }

    @Test
    fun should_recognizeKeywords_when_kotlinKeywordsPresent() {
        val code = "fun class val var if else when return"
        val tokens = KotlinLexer.tokenize(code)
        val keywords = tokens.filter { it.type == TokenType.KEYWORD }.map { it.text }
        assertTrue(keywords.containsAll(listOf("fun", "class", "val", "var", "if", "else", "when", "return")))
    }

    @Test
    fun should_recognizeString_when_doubleQuoteString() {
        val code = """val s = "hello world""""
        val tokens = KotlinLexer.tokenize(code)
        val strings = tokens.filter { it.type == TokenType.STRING }
        assertTrue(strings.isNotEmpty())
        assertEquals("\"hello world\"", strings.first().text)
    }

    @Test
    fun should_recognizeTripleQuoteString_when_multilineString() {
        val code = "val s = \"\"\"hello\nworld\"\"\""
        val tokens = KotlinLexer.tokenize(code)
        val strings = tokens.filter { it.type == TokenType.STRING }
        assertTrue(strings.isNotEmpty())
    }

    @Test
    fun should_recognizeSingleLineComment_when_doubleSlashComment() {
        val code = "// this is a comment\nval x = 1"
        val tokens = KotlinLexer.tokenize(code)
        val comments = tokens.filter { it.type == TokenType.COMMENT }
        assertTrue(comments.isNotEmpty())
        assertTrue(comments.first().text.startsWith("//"))
    }

    @Test
    fun should_recognizeMultiLineComment_when_slashStarComment() {
        val code = "/* multi\nline\ncomment */"
        val tokens = KotlinLexer.tokenize(code)
        val comments = tokens.filter { it.type == TokenType.COMMENT }
        assertTrue(comments.isNotEmpty())
    }

    @Test
    fun should_recognizeAnnotation_when_atSymbolPresent() {
        val code = "@Composable fun MyComposable() {}"
        val tokens = KotlinLexer.tokenize(code)
        val annotations = tokens.filter { it.type == TokenType.ANNOTATION }
        assertTrue(annotations.isNotEmpty())
        assertEquals("@Composable", annotations.first().text)
    }

    @Test
    fun should_recognizeNumber_when_integerLiteral() {
        val code = "val x = 42"
        val tokens = KotlinLexer.tokenize(code)
        val numbers = tokens.filter { it.type == TokenType.NUMBER }
        assertTrue(numbers.isNotEmpty())
        assertEquals("42", numbers.first().text)
    }

    @Test
    fun should_recognizeHexNumber_when_hexLiteral() {
        val code = "val x = 0xFF"
        val tokens = KotlinLexer.tokenize(code)
        val numbers = tokens.filter { it.type == TokenType.NUMBER }
        assertTrue(numbers.isNotEmpty())
        assertEquals("0xFF", numbers.first().text)
    }

    @Test
    fun should_recognizeBuiltinType_when_kotlinTypes() {
        val code = "val x: Int = 1\nval s: String = \"\""
        val tokens = KotlinLexer.tokenize(code)
        val types = tokens.filter { it.type == TokenType.TYPE }.map { it.text }
        assertTrue(types.contains("Int"))
        assertTrue(types.contains("String"))
    }

    @Test
    fun should_coverAllCharacters_when_tokenizing() {
        val code = "fun hello() {\n    println(\"world\")\n}"
        val tokens = KotlinLexer.tokenize(code)
        // 验证所有字符都被覆盖
        val reconstructed = tokens.joinToString("") { it.text }
        assertEquals(code, reconstructed)
    }

    @Test
    fun should_recognizeFunction_when_functionCall() {
        val code = "println(\"hello\")"
        val tokens = KotlinLexer.tokenize(code)
        val functions = tokens.filter { it.type == TokenType.FUNCTION }
        assertTrue(functions.isNotEmpty())
        assertEquals("println", functions.first().text)
    }
}
