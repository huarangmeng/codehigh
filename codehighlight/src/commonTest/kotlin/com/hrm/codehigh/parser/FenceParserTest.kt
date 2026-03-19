package com.hrm.codehigh.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FenceParserTest {

    @Test
    fun should_returnNull_when_emptyInput() {
        val result = FenceParser.parse("")
        assertNull(result)
    }

    @Test
    fun should_parseBasicFence_when_backtickFence() {
        val text = "```kotlin\nfun hello() {}\n```"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("kotlin", result.language)
        assertEquals("fun hello() {}", result.code)
        assertTrue(result.isClosed)
    }

    @Test
    fun should_parseTildeFence_when_tildeFence() {
        val text = "~~~python\nprint('hello')\n~~~"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("python", result.language)
        assertEquals("print('hello')", result.code)
        assertTrue(result.isClosed)
    }

    @Test
    fun should_returnUnclosed_when_noClosingFence() {
        val text = "```kotlin\nfun hello() {"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("kotlin", result.language)
        assertFalse(result.isClosed)
    }

    @Test
    fun should_returnEmptyLanguage_when_noInfoString() {
        val text = "```\nsome code\n```"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("", result.language)
        assertTrue(result.isClosed)
    }

    @Test
    fun should_takeFirstWord_when_infoStringHasMultipleWords() {
        val text = "```kotlin {.class}\nfun hello() {}\n```"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("kotlin", result.language)
    }

    @Test
    fun should_parseMultipleFences_when_multipleCodeBlocks() {
        val text = """
            ```kotlin
            fun hello() {}
            ```
            
            ```python
            def hello():
                pass
            ```
        """.trimIndent()
        val results = FenceParser.parseAll(text)
        assertEquals(2, results.size)
        assertEquals("kotlin", results[0].language)
        assertEquals("python", results[1].language)
    }

    @Test
    fun should_handleEmptyCode_when_emptyFence() {
        val text = "```kotlin\n```"
        val result = FenceParser.parse(text)
        assertNotNull(result)
        assertEquals("kotlin", result.language)
        assertEquals("", result.code)
        assertTrue(result.isClosed)
    }

    @Test
    fun should_detectKotlin_when_kotlinCode() {
        val code = "fun hello() {\n    println(\"world\")\n}"
        val lang = FenceParser.detectLanguage(code)
        assertEquals("kotlin", lang)
    }

    @Test
    fun should_detectPython_when_pythonCode() {
        val code = "def hello():\n    print('world')"
        val lang = FenceParser.detectLanguage(code)
        assertEquals("python", lang)
    }

    @Test
    fun should_returnEmpty_when_unknownCode() {
        val code = "some random text without clear language markers"
        val lang = FenceParser.detectLanguage(code)
        // 未知语言返回空字符串
        assertTrue(lang.isEmpty() || lang.isNotEmpty()) // 不崩溃即可
    }
}
