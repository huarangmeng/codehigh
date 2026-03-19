package com.hrm.codehigh.stream

import com.hrm.codehigh.ast.CodeAst
import com.hrm.codehigh.ast.CodeToken

/**
 * AST 差异引擎，对比新旧 CodeAst，计算最小变更 Token 集合。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 */
internal object AstDiffEngine {

    /**
     * 差异结果，包含稳定 Token 和变更 Token。
     *
     * @param stableTokens 未变更的稳定 Token 列表
     * @param changedTokens 发生变更的 Token 列表
     * @param stableCount 稳定前缀的 Token 数量
     */
    data class DiffResult(
        val stableTokens: List<CodeToken>,
        val changedTokens: List<CodeToken>,
        val stableCount: Int
    )

    /**
     * 对比新旧 AST，计算最小变更集合。
     *
     * @param oldAst 旧的 AST
     * @param newAst 新的 AST
     * @return 差异结果
     */
    fun diff(oldAst: CodeAst, newAst: CodeAst): DiffResult {
        val oldTokens = oldAst.tokens
        val newTokens = newAst.tokens

        // 找到第一个不同的 Token 位置
        var stableCount = 0
        val minSize = minOf(oldTokens.size, newTokens.size)

        while (stableCount < minSize) {
            val oldToken = oldTokens[stableCount]
            val newToken = newTokens[stableCount]
            if (oldToken.type == newToken.type && oldToken.text == newToken.text && oldToken.range == newToken.range) {
                stableCount++
            } else {
                break
            }
        }

        val stableTokens = newTokens.subList(0, stableCount)
        val changedTokens = newTokens.subList(stableCount, newTokens.size)

        return DiffResult(stableTokens, changedTokens, stableCount)
    }
}
