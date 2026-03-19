package com.hrm.codehigh.lexer

import com.hrm.codehigh.ast.CodeToken

/**
 * 词法分析器接口，定义代码分词的核心方法。
 * 对外公开，支持外部注入自定义 Lexer 实现。
 */
public interface Lexer {
    /**
     * 对代码字符串进行词法分析，返回 Token 列表。
     * 空输入返回空列表，不抛出异常。
     * 所有 Token 的 range 覆盖原始字符串完整范围（无遗漏字符）。
     *
     * @param code 待分析的代码字符串
     * @return Token 列表
     */
    public fun tokenize(code: String): List<CodeToken>
}
