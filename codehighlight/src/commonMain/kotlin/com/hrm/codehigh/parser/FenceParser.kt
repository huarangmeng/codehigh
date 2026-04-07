package com.hrm.codehigh.parser

/**
 * Markdown 代码围栏解析器。
 * 对外公开，支持反引号围栏（≥3个）和波浪线围栏（≥3个）。
 * 符合 CommonMark 规范：https://spec.commonmark.org/0.31.2/#fenced-code-blocks
 */
public object FenceParser {

    /**
     * 解析单个代码围栏，提取语言标识符和代码内容。
     * 支持流式场景：未闭合围栏仍返回已有内容，不等待闭合符号。
     *
     * @param text 包含代码围栏的文本
     * @return 解析结果，如果没有找到围栏则返回 null
     */
    public fun parse(text: String): FenceBlock? {
        val blocks = parseAll(text)
        return blocks.firstOrNull()
    }

    /**
     * 解析文本中所有代码围栏。
     * 代码块之间的普通文本不包含在结果中。
     *
     * @param text 包含代码围栏的文本
     * @return 所有代码围栏的解析结果列表
     */
    public fun parseAll(text: String): List<FenceBlock> {
        if (text.isEmpty()) return emptyList()
        val blocks = mutableListOf<FenceBlock>()
        val lines = text.split("\n")
        var i = 0

        while (i < lines.size) {
            val line = lines[i]
            val trimmed = line.trimStart()

            // 检测围栏开始行
            val fenceChar = when {
                trimmed.startsWith("```") -> '`'
                trimmed.startsWith("~~~") -> '~'
                else -> {
                    i++
                    continue
                }
            }

            // 计算围栏字符数量（至少3个）
            var fenceLen = 0
            while (fenceLen < trimmed.length && trimmed[fenceLen] == fenceChar) fenceLen++

            // 提取 info string（语言标识符）
            val infoString = trimmed.substring(fenceLen).trim()
            // info string 仅取第一个词作为语言标识符
            val language = infoString.split(Regex("\\s+")).firstOrNull()?.trim() ?: ""

            // 收集代码内容
            val codeLines = mutableListOf<String>()
            var isClosed = false
            i++

            while (i < lines.size) {
                val codeLine = lines[i]
                val codeTrimmed = codeLine.trimStart()

                // 检测围栏结束行：相同字符，数量 >= 开始行，且 info string 为空
                if (codeTrimmed.startsWith(fenceChar.toString().repeat(fenceLen))) {
                    var closeFenceLen = 0
                    while (closeFenceLen < codeTrimmed.length && codeTrimmed[closeFenceLen] == fenceChar) closeFenceLen++
                    val afterFence = codeTrimmed.substring(closeFenceLen).trim()
                    if (closeFenceLen >= fenceLen && afterFence.isEmpty()) {
                        isClosed = true
                        i++
                        break
                    }
                }

                codeLines.add(codeLine)
                i++
            }

            val code = codeLines.joinToString("\n")
            blocks.add(FenceBlock(language, code, isClosed))
        }

        return blocks
    }

    /**
     * 启发式语言检测，基于关键词规则。
     * 标记为 internal，仅供模块内部使用。
     *
     * @param code 代码字符串
     * @return 检测到的语言标识符，检测失败时返回空字符串
     */
    internal fun detectLanguage(code: String): String {
        if (code.isBlank()) return ""

        val trimmed = code.trim()

        // Kotlin 特征
        if (trimmed.contains(Regex("\\bfun\\s+\\w+\\s*\\(")) ||
            trimmed.contains(Regex("\\bval\\s+\\w+\\s*[:=]")) ||
            trimmed.contains(Regex("\\bvar\\s+\\w+\\s*[:=]")) ||
            trimmed.contains("@Composable") || trimmed.contains("@JvmStatic")) {
            return "kotlin"
        }

        if (trimmed.contains("<?php") ||
            trimmed.contains(Regex("\\$\\w+\\s*=")) ||
            trimmed.contains(Regex("\\becho\\s+['\"]")) ||
            trimmed.contains(Regex("\\bfunction\\s+\\w+\\s*\\(")) && trimmed.contains("->")) {
            return "php"
        }

        // Python 特征
        if (trimmed.contains(Regex("\\bdef\\s+\\w+\\s*\\([^)]*\\)\\s*:")) ||
            trimmed.contains(Regex("\\bimport\\s+\\w+")) && trimmed.contains(Regex("\\bprint\\s*\\(")) ||
            trimmed.contains(Regex("^\\s*#!.*python", RegexOption.MULTILINE))) {
            return "python"
        }

        if (trimmed.contains(Regex("\\bclass\\s+\\w+\\s+extends\\s+(StatelessWidget|StatefulWidget)")) ||
            trimmed.contains(Regex("\\bWidget\\s+build\\s*\\(")) ||
            trimmed.contains("setState(") ||
            trimmed.contains(Regex("\\brequired\\s+this\\."))) {
            return "dart"
        }

        if (trimmed.contains(Regex("\\bdefmodule\\s+[A-Z]\\w*")) ||
            trimmed.contains("|>") ||
            trimmed.contains("%{") ||
            trimmed.contains(Regex("\\bIO\\.puts\\s*\\("))) {
            return "elixir"
        }

        if (trimmed.contains(Regex("\\bcase\\s+class\\s+\\w+")) ||
            trimmed.contains(Regex("\\btrait\\s+\\w+")) ||
            trimmed.contains(Regex("\\bobject\\s+\\w+")) ||
            trimmed.contains(Regex("\\bimplicit\\b"))) {
            return "scala"
        }

        if (trimmed.contains(Regex("\\bdef\\s+\\w+[!?]?")) &&
            (trimmed.contains(Regex("\\bputs\\b")) || trimmed.contains(Regex("\\battr_accessor\\b")) || trimmed.contains("@"))) {
            return "ruby"
        }

        if (trimmed.contains(Regex("\\bfunction\\s*\\(")) ||
            trimmed.contains(Regex("\\blibrary\\s*\\(")) ||
            trimmed.contains(Regex("\\bdata\\.frame\\s*\\(")) ||
            trimmed.contains("<-")) {
            return "r"
        }

        if (trimmed.contains(Regex("(?m)^\\s*(FROM|RUN|COPY|CMD|EXPOSE|ENV|WORKDIR|ENTRYPOINT|ARG)\\b", RegexOption.IGNORE_CASE))) {
            return "dockerfile"
        }

        if (trimmed.contains(Regex("(?m)^\\s*\\[[^\\]]+\\]\\s*$")) ||
            trimmed.contains(Regex("(?m)^\\s*\\[\\[[^\\]]+\\]\\]\\s*$"))) {
            return "toml"
        }

        if (trimmed.contains(Regex("\\blocal\\s+function\\s+\\w+")) ||
            trimmed.contains(Regex("\\brequire\\s*\\(?['\"]")) ||
            trimmed.contains(Regex("(?m)^\\s*--"))) {
            return "lua"
        }

        if (trimmed.contains(Regex("\\bmodule\\s+[A-Z]\\w*(\\.[A-Z]\\w*)*\\s+where")) ||
            trimmed.contains(Regex("\\bdata\\s+[A-Z]\\w*\\s*=")) ||
            trimmed.contains(Regex("\\binstance\\b")) ||
            trimmed.contains("::")) {
            return "haskell"
        }

        // JavaScript/TypeScript 特征
        if (trimmed.contains(Regex("\\bconst\\s+\\w+\\s*=")) ||
            trimmed.contains(Regex("\\bfunction\\s+\\w+\\s*\\(")) ||
            trimmed.contains(Regex("\\b(let|var)\\s+\\w+\\s*="))) {
            if (trimmed.contains(Regex(":\\s*(string|number|boolean|any|void)")) ||
                trimmed.contains(Regex("\\binterface\\s+\\w+")) ||
                trimmed.contains(Regex("\\btype\\s+\\w+\\s*="))) {
                return "typescript"
            }
            return "javascript"
        }

        // Java 特征
        if (trimmed.contains(Regex("\\bpublic\\s+(class|interface|enum)\\s+\\w+")) ||
            trimmed.contains(Regex("\\bSystem\\.out\\.println\\s*\\(")) ||
            trimmed.contains(Regex("\\bimport\\s+java\\."))) {
            return "java"
        }

        // Go 特征
        if (trimmed.contains(Regex("\\bpackage\\s+\\w+")) ||
            trimmed.contains(Regex("\\bfunc\\s+\\w+\\s*\\(")) ||
            trimmed.contains(Regex("\\bfmt\\.Print"))) {
            return "go"
        }

        // Rust 特征
        if (trimmed.contains(Regex("\\bfn\\s+\\w+\\s*\\(")) ||
            trimmed.contains(Regex("\\blet\\s+mut\\s+\\w+")) ||
            trimmed.contains(Regex("\\buse\\s+std::"))) {
            return "rust"
        }

        // SQL 特征
        if (trimmed.uppercase().contains(Regex("\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP)\\b"))) {
            return "sql"
        }

        // JSON 特征
        if ((trimmed.startsWith("{") && trimmed.endsWith("}")) ||
            (trimmed.startsWith("[") && trimmed.endsWith("]"))) {
            if (trimmed.contains(Regex("\"\\w+\"\\s*:"))) {
                return "json"
            }
        }

        // YAML 特征
        if (trimmed.contains(Regex("^\\w+:\\s*\\S", RegexOption.MULTILINE)) &&
            !trimmed.contains("{") && !trimmed.contains(";")) {
            return "yaml"
        }

        // Bash 特征
        if (trimmed.startsWith("#!/") || trimmed.startsWith("$ ") ||
            trimmed.contains(Regex("\\b(echo|grep|sed|awk|chmod)\\s"))) {
            return "bash"
        }

        // HTML 特征
        if (trimmed.contains(Regex("<html|<!DOCTYPE", RegexOption.IGNORE_CASE)) ||
            trimmed.contains(Regex("<(div|span|p|a|img|head|body)\\b", RegexOption.IGNORE_CASE))) {
            return "html"
        }

        // XML 特征
        if (trimmed.startsWith("<?xml") || trimmed.contains(Regex("<\\w+[^>]*>.*</\\w+>"))) {
            return "xml"
        }

        // CSS 特征
        if (trimmed.contains(Regex("\\{[^}]*:[^}]*\\}")) &&
            (trimmed.contains(Regex("\\.(\\w+)\\s*\\{")) || trimmed.contains(Regex("#(\\w+)\\s*\\{")))) {
            return "css"
        }

        return ""
    }
}
