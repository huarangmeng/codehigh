package com.hrm.codehigh.i18n

import kotlin.math.max

/**
 * 多语言字符串支持
 * 支持中文/英文，不支持的语言回退到英文
 */
internal object Strings {
    /**
     * 获取当前语言代码
     * 在 JVM/Android 上使用系统语言，其他平台默认英文
     */
    private val languageCode: String by lazy {
        getSystemLanguage()
    }

    private fun getSystemLanguage(): String {
        return try {
            // 尝试获取系统语言
            val locale = PlatformLocale.current()
            locale.language.lowercase()
        } catch (e: Exception) {
            "en"
        }
    }

    /**
     * 是否为中文环境
     */
    private val isChinese: Boolean
        get() = languageCode.startsWith("zh")

    /**
     * 收起按钮文本
     */
    fun collapse(): String = if (isChinese) "▲ 收起" else "▲ Collapse"

    /**
     * 展开按钮文本
     * @param hiddenLines 隐藏的行数
     */
    fun expand(hiddenLines: Int): String {
        val lines = max(0, hiddenLines)
        return if (isChinese) {
            "▼ 展开 ($lines 行)"
        } else {
            "▼ Expand ($lines lines)"
        }
    }

    /**
     * 复制按钮文本
     */
    fun copy(): String = if (isChinese) "复制" else "Copy"

    /**
     * 已复制提示文本
     */
    fun copied(): String = if (isChinese) "已复制" else "Copied"

    /**
     * 行号列标题
     */
    fun lineNumber(): String = if (isChinese) "行" else "Line"
}

/**
 * 平台相关的 Locale 获取
 */
internal expect object PlatformLocale {
    fun current(): LocaleInfo
}

/**
 * 语言环境信息
 */
internal data class LocaleInfo(
    val language: String,
    val country: String = ""
)
