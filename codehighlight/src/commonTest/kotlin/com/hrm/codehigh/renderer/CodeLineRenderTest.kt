package com.hrm.codehigh.renderer

import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.ast.TokenType
import com.hrm.codehigh.theme.OneDarkProTheme
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeLineRenderTest {

    @Test
    fun should_markHighlightedLines_when_highlightedLinesProvided() {
        val renders = buildLineRenders(
            sourceLines = listOf("fun hello()", "println(\"ok\")", "return"),
            tokens = listOf(
                CodeToken(TokenType.KEYWORD, "fun", 0 until 3),
                CodeToken(TokenType.PLAIN, " hello()", 3 until 11),
                CodeToken(TokenType.PLAIN, "\n", 11 until 12),
                CodeToken(TokenType.FUNCTION, "println", 12 until 19),
                CodeToken(TokenType.PLAIN, "(\"ok\")", 19 until 25),
                CodeToken(TokenType.PLAIN, "\n", 25 until 26),
                CodeToken(TokenType.KEYWORD, "return", 26 until 32)
            ),
            theme = OneDarkProTheme,
            language = "kotlin",
            highlightedLines = setOf(2)
        )

        assertEquals(CodeLineKind.NORMAL, renders[0].kind)
        assertEquals(CodeLineKind.HIGHLIGHTED, renders[1].kind)
        assertEquals(CodeLineKind.NORMAL, renders[2].kind)
    }

    @Test
    fun should_markDiffLines_when_diffLanguageProvided() {
        assertEquals(CodeLineKind.DIFF_META_HEADER, resolveLineKind(0, "diff --git a/a.kt b/a.kt", "diff", emptySet()))
        assertEquals(CodeLineKind.DIFF_META_HUNK, resolveLineKind(1, "@@ -1,2 +1,2 @@", "diff", emptySet()))
        assertEquals(CodeLineKind.DIFF_REMOVED, resolveLineKind(2, "-old line", "diff", emptySet()))
        assertEquals(CodeLineKind.DIFF_ADDED, resolveLineKind(3, "+new line", "diff", emptySet()))
        assertEquals(CodeLineKind.NORMAL, resolveLineKind(4, " unchanged", "diff", emptySet()))
    }
}
