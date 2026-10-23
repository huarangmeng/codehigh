package com.hrm.codehigh.i18n

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.localeIdentifier

internal actual object PlatformLocale {
    actual fun current(): LocaleInfo {
        val locale = NSLocale.currentLocale
        val lang = locale.languageCode ?: "en"
        // 从 localeIdentifier 中提取国家代码，如 "zh_CN" -> "CN"
        val localeId = locale.localeIdentifier ?: ""
        val country = if (localeId.contains("_")) {
            localeId.substringAfter("_").substringBefore("@")
        } else {
            ""
        }
        return LocaleInfo(
            language = lang,
            country = country
        )
    }
}
