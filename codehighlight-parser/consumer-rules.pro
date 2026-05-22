# =============================================================
# codehighlight-parser consumer ProGuard / R8 rules
# 这些规则会被打包进 AAR，并在依赖方启用 R8/Proguard 时自动生效。
# 仅保留模块对外公开的 API，避免下游应用混淆 / 收缩时破坏使用方代码。
# =============================================================

# --- 公开数据模型与枚举（外部按类名 / 字段名访问） -----------------
-keep public class com.hrm.codehigh.ast.CodeAst { *; }
-keep public class com.hrm.codehigh.ast.CodeToken { *; }
-keep public class com.hrm.codehigh.ast.TokenType { *; }

# 保留 enum 的内置方法，防止下游通过 valueOf / values 反射失败
-keepclassmembers enum com.hrm.codehigh.ast.TokenType {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --- 公开词法分析接口与注册表 -----------------------------------
-keep public interface com.hrm.codehigh.lexer.Lexer { *; }
-keep public class com.hrm.codehigh.lexer.LanguageRegistry { *; }
-keep public class com.hrm.codehigh.lexer.LanguageRegistry$* { *; }

# --- 公开增量高亮 API ------------------------------------------
-keep public class com.hrm.codehigh.stream.IncrementalHighlighter { *; }
-keep public class com.hrm.codehigh.stream.IncrementalHighlighter$* { *; }

# 抑制 Kotlin 元数据相关 note，避免下游构建噪声
-dontwarn kotlin.**
-dontwarn kotlinx.**
