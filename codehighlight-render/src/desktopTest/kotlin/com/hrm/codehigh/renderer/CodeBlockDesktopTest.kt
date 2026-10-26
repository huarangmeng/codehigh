package com.hrm.codehigh.renderer

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runSkikoComposeUiTest
import com.hrm.codehigh.i18n.Strings
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class CodeBlockDesktopTest {
    @Test
    fun should_renderHighlightedCodeAndExpand_when_runningOnDesktop() = runSkikoComposeUiTest(
        size = Size(512f, 256f),
    ) {
        val colors = CodeDefaults.lightColors()
        setContent {
            CodeBlock(
                code = "fun first() = 1\nval second = 2\nval third = \"native\"",
                language = "kotlin",
                modifier = Modifier.testTag("code"),
                colors = colors,
                maxVisibleLines = 1,
                showCopyButton = false,
            )
        }

        onNodeWithText("fun first() = 1").assertIsDisplayed()
        onNodeWithText("val third = \"native\"").assertDoesNotExist()
        val collapsedPixels = onNodeWithTag("code").captureToImage().toPixelMap()
        var keywordPixels = 0
        for (y in 0 until collapsedPixels.height) {
            for (x in 0 until collapsedPixels.width) {
                val pixel = collapsedPixels[x, y]
                if (abs(pixel.red - colors.keyword.red) < 0.1f &&
                    abs(pixel.green - colors.keyword.green) < 0.1f &&
                    abs(pixel.blue - colors.keyword.blue) < 0.1f
                ) {
                    keywordPixels++
                }
            }
        }
        assertTrue(keywordPixels > 2, "The first code line must contain rendered keyword pixels")

        onNodeWithText(Strings.expand(2)).performClick()
        onNodeWithText("val third = \"native\"").assertIsDisplayed()
        val expandedPixels = onNodeWithTag("code").captureToImage().toPixelMap()
        assertTrue(expandedPixels.height > collapsedPixels.height)

        onNodeWithText(Strings.collapse()).performClick()
        onNodeWithText("val third = \"native\"").assertDoesNotExist()
    }
}
