package com.hrm.codehigh.ast

/**
 * 代码 AST（抽象语法树）结构，持有词法分析结果。
 * 作为 parser 层结果公开，供 render 层和纯解析使用方复用。
 *
 * @param tokens Token 列表，覆盖原始字符串完整范围（无遗漏字符）
 * @param source 原始字符串引用，用于增量更新时的前缀比对
 * @param language 对应的语言标识符
 */
data class CodeAst(
    val tokens: List<CodeToken>,
    val source: String,
    val language: String
)
