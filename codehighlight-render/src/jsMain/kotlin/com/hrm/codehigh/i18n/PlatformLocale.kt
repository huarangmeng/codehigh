package com.hrm.codehigh.i18n

import kotlin.js.js

internal actual object PlatformLocale {
    actual fun current(): LocaleInfo {
        return try {
            val navigator = js("window.navigator")
            val lang = (navigator.language as? String) ?: "en"
            val parts = lang.split("-")
            LocaleInfo(
                language = parts.getOrNull(0) ?: "en",
                country = parts.getOrNull(1) ?: ""
            )
        } catch (e: Exception) {
            LocaleInfo(language = "en")
        }
    }
}
