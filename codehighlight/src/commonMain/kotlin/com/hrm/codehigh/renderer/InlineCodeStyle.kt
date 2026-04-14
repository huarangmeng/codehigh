package com.hrm.codehigh.renderer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.ast.TokenType
import com.hrm.codehigh.theme.CodeTheme
import com.hrm.codehigh.theme.LocalCodeTheme

data class InlineCodeStyle(
    val theme: CodeTheme,
    val textStyle: TextStyle,
    val containerColor: Color,
    val borderColor: Color? = null,
    val borderWidth: Dp = 0.dp,
    val shape: Shape,
    val contentPadding: PaddingValues,
)

object InlineCodeDefaults {
    @Composable
    fun style(): InlineCodeStyle = style(LocalCodeTheme.current)

    fun style(theme: CodeTheme): InlineCodeStyle = InlineCodeStyle(
        theme = theme,
        textStyle = TextStyle(
            color = theme.colorFor(TokenType.PLAIN),
            fontSize = 13.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily.Monospace,
        ),
        containerColor = if (theme.isDark) Color(0xFF30363D) else Color(0xFFF6F8FA),
        borderColor = if (theme.isDark) Color(0xFF3D444D) else Color(0xFFD0D7DE),
        borderWidth = 1.dp,
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
    )
}
