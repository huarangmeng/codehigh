package com.hrm.codehigh.i18n

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformLocaleTest {
    @Test
    fun should_useSystemLanguage_when_runningOnApplePlatform() {
        assertEquals(NSLocale.currentLocale.languageCode, PlatformLocale.current().language)
        val chinese = NSLocale.currentLocale.languageCode.lowercase().startsWith("zh")
        assertEquals(if (chinese) "复制" else "Copy", Strings.copy())
        assertEquals(if (chinese) "▼ 展开 (2 行)" else "▼ Expand (2 lines)", Strings.expand(2))
    }
}
