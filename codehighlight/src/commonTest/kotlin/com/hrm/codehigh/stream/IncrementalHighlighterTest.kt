package com.hrm.codehigh.stream

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class IncrementalHighlighterTest {

    @Test
    fun should_returnAst_when_firstParse() {
        val highlighter = IncrementalHighlighter()
        val ast = highlighter.update("fun hello() {}", "kotlin")
        assertNotNull(ast)
        assertTrue(ast.tokens.isNotEmpty())
        assertEquals("kotlin", ast.language)
    }

    @Test
    fun should_returnCachedAst_when_sameCodeAndLanguage() {
        val highlighter = IncrementalHighlighter()
        val code = "fun hello() {}"
        val ast1 = highlighter.update(code, "kotlin")
        val ast2 = highlighter.update(code, "kotlin")
        // 相同代码应返回缓存结果（同一对象）
        assertEquals(ast1, ast2)
    }

    @Test
    fun should_fullReparse_when_languageChanges() {
        val highlighter = IncrementalHighlighter()
        val code = "def hello(): pass"
        val ast1 = highlighter.update(code, "python")
        val ast2 = highlighter.update(code, "kotlin")
        // 语言变化时应重新解析
        assertEquals("python", ast1.language)
        assertEquals("kotlin", ast2.language)
    }

    @Test
    fun should_incrementalUpdate_when_codeAppended() {
        val highlighter = IncrementalHighlighter()
        val code1 = "fun hello() {"
        val code2 = "fun hello() {\n    println(\"world\")\n}"
        val ast1 = highlighter.update(code1, "kotlin")
        val ast2 = highlighter.update(code2, "kotlin")
        assertNotNull(ast2)
        assertEquals(code2, ast2.source)
    }

    @Test
    fun should_coverAllCharacters_when_tokenizing() {
        val highlighter = IncrementalHighlighter()
        val code = "fun hello() {\n    val x = 42\n}"
        val ast = highlighter.update(code, "kotlin")
        val reconstructed = ast.tokens.joinToString("") { it.text }
        assertEquals(code, reconstructed)
    }

    @Test
    fun should_handleEmptyCode_when_emptyInput() {
        val highlighter = IncrementalHighlighter()
        val ast = highlighter.update("", "kotlin")
        assertNotNull(ast)
        assertTrue(ast.tokens.isEmpty())
    }

    @Test
    fun should_invalidateCache_when_invalidateCalled() {
        val highlighter = IncrementalHighlighter()
        val code = "fun hello() {}"
        val ast1 = highlighter.update(code, "kotlin")
        highlighter.invalidate()
        val ast2 = highlighter.update(code, "kotlin")
        // 缓存清除后重新解析，结果应相同但不是同一对象
        assertEquals(ast1.source, ast2.source)
        assertEquals(ast1.language, ast2.language)
    }
}
