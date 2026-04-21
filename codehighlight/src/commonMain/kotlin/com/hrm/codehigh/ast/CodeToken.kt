package com.hrm.codehigh.ast

/**
 * 代码 Token 数据结构，表示词法分析后的最小语义单元。
 * 对外公开，供 onTokenClick 回调使用。
 *
 * @param type Token 类型
 * @param text Token 原始文本
 * @param range Token 在原始字符串中的位置范围
 */
data class CodeToken(
    val type: TokenType,
    val text: String,
    val range: IntRange
)
