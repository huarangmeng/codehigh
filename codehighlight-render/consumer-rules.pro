# =============================================================================
# codehighlight-render 消费方保留规则（Consumer ProGuard Rules）
#
# 该文件随 AAR 一起发布，会在使用本库的 Android 应用进行 R8/ProGuard 优化时自动生效。
#
# 编写原则（参考 Android 官方《库作者优化指南》）：
#  - 仅保留运行时反射 / JNI / 序列化等无法被 R8 静态分析到的入口点。
#  - 严禁包级 -keep（如 -keep com.hrm.codehigh.** 会导致下游 App 体积膨胀）。
#  - 严禁全局选项（如 -dontobfuscate / -allowaccessmodification / -ignorewarnings）。
# =============================================================================

# codehighlight-render 仅提供 Compose 渲染层（CodeBlock / InlineCode / 主题 / 样式），
# 所有公开 API 均由调用方直接 Kotlin/Compose 调用，未使用反射、JNI、ServiceLoader、
# 注解处理或运行时序列化，因此无需任何 -keep 规则。
# 仅显式声明本库对类元数据属性的依赖，避免下游 App 显式禁用这些属性时崩溃。

# Kotlin / Compose 反射所需的类元数据
# （Companion / data class / sealed 子类 / 嵌套 lambda / ComposableSingletons$* 等）
-keepattributes InnerClasses,EnclosingMethod,Signature

# Kotlin null 检查 / suspend 函数 / @Metadata 等运行时使用的注解
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations

# -----------------------------------------------------------------------------
# Compose Desktop / 普通 JVM ProGuard 兼容（仅压制 fatal warning，不阻止 shrink）
# -----------------------------------------------------------------------------
# Kotlin 编译器在 `companion object` 中 `private inline fun` + `private val` 组合下
# 会把私有字段提升到 outer 类的 synthetic 静态字段；某些 Kotlin/KMP 版本组合下，
# `*$Companion.class` 仍按原名引用 outer 类的字段，被 ProGuard 全图静态校验视为
# unresolved 并升级为 fatal。运行期 JVM 惰性解析不触发，仅构建期失败。
# 仅压制对应 warning，不使用 `-keep`，避免阻止下游 shrink。
-dontwarn com.hrm.codehigh.**

