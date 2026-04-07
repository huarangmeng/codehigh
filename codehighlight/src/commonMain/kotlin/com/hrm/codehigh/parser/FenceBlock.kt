package com.hrm.codehigh.parser

/**
 * 代码围栏解析结果。
 * 对外公开，供调用方使用。
 *
 * @param language 语言标识符（可能为空字符串）
 * @param code 代码内容
 * @param isClosed 围栏是否已闭合（流式场景中可能未闭合）
 */
public data class FenceBlock(
    val language: String,
    val code: String,
    val isClosed: Boolean,
    val highlightedLines: Set<Int> = emptySet(),
    val startLine: Int = 1
)
