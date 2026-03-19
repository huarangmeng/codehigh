package com.hrm.codehigh.theme

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class CodeThemeTest {

    @Test
    fun should_returnColor_when_validTokenType() {
        val theme = OneDarkProTheme
        val color = theme.colorFor(TokenType.KEYWORD)
        assertNotNull(color)
        assertNotEquals(Color.Unspecified, color)
    }

    @Test
    fun should_coverAllTokenTypes_when_oneDarkProTheme() {
        val theme = OneDarkProTheme
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_githubLightTheme() {
        val theme = GithubLightTheme
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_draculaProTheme() {
        val theme = DraculaProTheme
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_solarizedLightTheme() {
        val theme = SolarizedLightTheme
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_beDark_when_oneDarkProTheme() {
        assertEquals(true, OneDarkProTheme.isDark)
    }

    @Test
    fun should_beLight_when_githubLightTheme() {
        assertEquals(false, GithubLightTheme.isDark)
    }

    @Test
    fun should_beDark_when_draculaProTheme() {
        assertEquals(true, DraculaProTheme.isDark)
    }

    @Test
    fun should_beLight_when_solarizedLightTheme() {
        assertEquals(false, SolarizedLightTheme.isDark)
    }

    @Test
    fun should_fallbackToPlain_when_safeColorFor() {
        val theme = OneDarkProTheme
        val plainColor = theme.colorFor(TokenType.PLAIN)
        val safeColor = theme.safeColorFor(TokenType.PLAIN)
        assertEquals(plainColor, safeColor)
    }
}
