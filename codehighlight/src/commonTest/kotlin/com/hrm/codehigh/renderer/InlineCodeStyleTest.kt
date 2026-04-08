package com.hrm.codehigh.renderer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.theme.GithubLightTheme
import com.hrm.codehigh.theme.OneDarkProTheme
import kotlin.test.Test
import kotlin.test.assertEquals

class InlineCodeStyleTest {

    @Test
    fun should_embedGithubLikeDefaults_intoInlineStyle() {
        val style = InlineCodeDefaults.style(GithubLightTheme)

        assertEquals(GithubLightTheme, style.theme)
        assertEquals(Color(0xFF2E3440), style.textStyle.color)
        assertEquals(13.sp, style.textStyle.fontSize)
        assertEquals(20.sp, style.textStyle.lineHeight)
        assertEquals(6.dp, style.contentPadding.calculateLeftPadding(LayoutDirection.Ltr))
        assertEquals(6.dp, style.contentPadding.calculateRightPadding(LayoutDirection.Ltr))
        assertEquals(2.dp, style.contentPadding.calculateTopPadding())
        assertEquals(2.dp, style.contentPadding.calculateBottomPadding())
        assertEquals(Color(0xFFF5F7FA), style.containerColor)
        assertEquals(Color(0xFFE2E8F0), style.borderColor)
        assertEquals(1.dp, style.borderWidth)
    }

    @Test
    fun should_useSpecifiedDarkPalette_forInlineCode() {
        val style = InlineCodeDefaults.style(OneDarkProTheme)

        assertEquals(OneDarkProTheme, style.theme)
        assertEquals(Color(0xFFECEFF4), style.textStyle.color)
        assertEquals(Color(0xFF2A2F3A), style.containerColor)
        assertEquals(Color(0xFF3B4252), style.borderColor)
        assertEquals(1.dp, style.borderWidth)
    }

    @Test
    fun should_keepSingleStyleEntry_whenInlineStyleIsCustomized() {
        val baseStyle = InlineCodeDefaults.style(OneDarkProTheme)
        val style = baseStyle.copy(
            textStyle = baseStyle.textStyle.copy(fontSize = 14.sp),
            containerColor = Color.Magenta,
        )

        assertEquals(OneDarkProTheme, style.theme)
        assertEquals(Color(0xFFECEFF4), style.textStyle.color)
        assertEquals(14.sp, style.textStyle.fontSize)
        assertEquals(Color.Magenta, style.containerColor)
    }
}
