package com.hrm.codehigh.renderer

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 流式光标动画组件，仅在 isStreaming = true 时显示。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 *
 * @param isStreaming 是否处于流式输出状态
 * @param color 光标颜色
 */
@Composable
internal fun StreamingCursor(
    isStreaming: Boolean,
    color: Color = Color.White
) {
    if (!isStreaming) return

    val infiniteTransition = rememberInfiniteTransition(label = "cursor")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursorAlpha"
    )

    Box(
        modifier = Modifier
            .width(2.dp)
            .height(16.dp)
            .background(color.copy(alpha = alpha))
    )
}
