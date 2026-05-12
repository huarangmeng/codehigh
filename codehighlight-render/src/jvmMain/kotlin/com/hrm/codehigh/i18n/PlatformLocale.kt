package com.hrm.codehigh.i18n

import java.util.Locale

internal actual object PlatformLocale {
    actual fun current(): LocaleInfo {
        val locale = Locale.getDefault()
        return LocaleInfo(
            language = locale.language ?: "en",
            country = locale.country ?: ""
        )
    }
}
