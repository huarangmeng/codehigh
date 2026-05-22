# =============================================================
# codehighlight-render consumer ProGuard / R8 rules
# 这些规则会被打包进 AAR，并在依赖方启用 R8/Proguard 时自动生效。
# 仅保留模块对外公开的渲染 API，避免下游应用混淆 / 收缩时破坏使用方代码。
# =============================================================

# --- 公开渲染入口（@Composable 顶层函数） ----------------------
# Compose 顶层 @Composable 函数会被编译成 ComposableSingletons 等辅助类，
# 这里同时保留同名 *Kt 文件类，保证下游引用不被误删。
-keep public class com.hrm.codehigh.renderer.CodeBlockKt { *; }
-keep public class com.hrm.codehigh.renderer.InlineCodeKt { *; }
-keep public class com.hrm.codehigh.renderer.HighlightedStringKt { *; }
-keep public class com.hrm.codehigh.renderer.InlineCodeMeasurerKt { *; }

# --- 公开数据 / 样式类型 --------------------------------------
-keep public class com.hrm.codehigh.renderer.InlineCodeStyle { *; }
-keep public class com.hrm.codehigh.renderer.InlineCodeDefaults { *; }
-keep public class com.hrm.codehigh.renderer.InlineCodeSize { *; }

# --- 公开主题 API ---------------------------------------------
-keep public interface com.hrm.codehigh.theme.CodeTheme { *; }
-keep public class com.hrm.codehigh.theme.OneDarkProTheme { *; }
-keep public class com.hrm.codehigh.theme.DraculaProTheme { *; }
-keep public class com.hrm.codehigh.theme.GithubLightTheme { *; }
-keep public class com.hrm.codehigh.theme.SolarizedLightTheme { *; }
-keep public class com.hrm.codehigh.theme.LocalCodeThemeKt { *; }

# 抑制 Kotlin / Compose 元数据相关 note，避免下游构建噪声
-dontwarn kotlin.**
-dontwarn kotlinx.**
-dontwarn androidx.compose.**
