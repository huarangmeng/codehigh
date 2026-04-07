package com.hrm.codehigh.stream

import com.hrm.codehigh.ast.CodeAst
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.lexer.LanguageRegistry

/**
 * 增量高亮引擎，用于流式场景下的高效代码高亮更新。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 *
 * 核心策略：
 * 1. 稳定前缀 Token 直接复用，不重新解析
 * 2. 仅对尾部脏区域（从最后一个受影响 Token 到文本末尾）重新解析
 * 3. 相同代码字符串和语言命中 AST 缓存，直接返回缓存结果
 */
internal class IncrementalHighlighter {

    private companion object {
        private const val CONTEXT_LOOKBACK_CHARS = 64
    }

    /** AST 缓存：key = language + "|" + code */
    private var cachedAst: CodeAst? = null

    /** 上一次解析的语言 */
    private var lastLanguage: String = ""

    /**
     * 增量更新高亮结果。
     *
     * @param code 新的代码字符串
     * @param language 语言标识符
     * @return 更新后的 CodeAst
     */
    fun update(code: String, language: String): CodeAst {
        // 语言变化时触发全量重新解析
        if (language != lastLanguage) {
            lastLanguage = language
            cachedAst = null
        }

        val cached = cachedAst
        // 命中缓存
        if (cached != null && cached.source == code && cached.language == language) {
            return cached
        }

        // 尝试增量更新
        val newAst = if (cached != null && code.startsWith(cached.source)) {
            // 代码是旧代码的扩展（追加场景）
            incrementalParse(code, language, cached)
        } else {
            // 全量解析
            fullParse(code, language)
        }

        cachedAst = newAst
        return newAst
    }

    /**
     * 全量解析。
     */
    private fun fullParse(code: String, language: String): CodeAst {
        val lexer = LanguageRegistry.getOrPlain(language)
        val tokens = lexer.tokenize(code)
        return CodeAst(tokens, code, language)
    }

    /**
     * 增量解析：复用稳定前缀，仅重新解析尾部脏区域。
     */
    private fun incrementalParse(newCode: String, language: String, oldAst: CodeAst): CodeAst {
        val oldCode = oldAst.source
        val appendedStart = oldCode.length
        val reparseStart = determineReparseStart(oldAst, appendedStart)
        val stableTokens = oldAst.tokens.takeWhile { it.range.last < reparseStart }

        val dirtyCode = newCode.substring(reparseStart)
        val lexer = LanguageRegistry.getOrPlain(language)
        val dirtyTokens = lexer.tokenize(dirtyCode).map { token ->
            CodeToken(
                type = token.type,
                text = token.text,
                range = (token.range.first + reparseStart)..(token.range.last + reparseStart)
            )
        }

        val allTokens = if (reparseStart > 0) {
            stableTokens + dirtyTokens
        } else {
            dirtyTokens
        }

        return CodeAst(allTokens, newCode, language)
    }

    private fun determineReparseStart(oldAst: CodeAst, appendedStart: Int): Int {
        if (appendedStart == 0) return 0

        val oldCode = oldAst.source
        val nearbyTokenStart = oldAst.tokens
            .lastOrNull { it.range.last < appendedStart && appendedStart - it.range.first <= CONTEXT_LOOKBACK_CHARS }
            ?.range
            ?.first
        val unfinishedTokenStart = oldAst.tokens
            .asReversed()
            .firstOrNull {
                it.range.last < appendedStart &&
                    appendedStart - it.range.first <= CONTEXT_LOOKBACK_CHARS &&
                    shouldReparseFromTokenStart(it)
            }
            ?.range
            ?.first

        return unfinishedTokenStart ?: nearbyTokenStart ?: 0
    }

    private fun shouldReparseFromTokenStart(token: CodeToken): Boolean {
        return when (token.type) {
            com.hrm.codehigh.ast.TokenType.COMMENT -> token.text.startsWith("/*") && !token.text.endsWith("*/")
            com.hrm.codehigh.ast.TokenType.STRING -> {
                (token.text.startsWith("\"\"\"") && !token.text.endsWith("\"\"\"")) ||
                    (token.text.startsWith("\"") && !token.text.endsWith("\"")) ||
                    (token.text.startsWith("'") && !token.text.endsWith("'"))
            }
            else -> false
        }
    }

    /**
     * 清除缓存，强制下次全量解析。
     */
    fun invalidate() {
        cachedAst = null
        lastLanguage = ""
    }
}
