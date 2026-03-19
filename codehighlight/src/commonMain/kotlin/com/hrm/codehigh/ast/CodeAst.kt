package com.hrm.codehigh.ast

/**
 * 代码 AST（抽象语法树）结构，持有词法分析结果。
 * 标记为 internal，外部无需感知 AST 结构。
 *
 * @param tokens Token 列表，覆盖原始字符串完整范围（无遗漏字符）
 * @param source 原始字符串引用，用于增量更新时的前缀比对
 * @param language 对应的语言标识符
 */
internal data class CodeAst(
    val tokens: List<CodeToken>,
    val source: String,
    val language: String
)
