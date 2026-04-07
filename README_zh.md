# CodeHigh

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.10.0-brightgreen.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android API](https://img.shields.io/badge/Android%20API-23%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

基于 Kotlin Multiplatform (KMP) 和 Compose Multiplatform 开发的高性能跨平台代码高亮库，支持在 Android、iOS、Desktop (JVM) 和 Web (Wasm/JS) 平台上提供一致的渲染效果。

[English Version](./README.md)

## 🌟 核心特性

- **多端统一渲染**：使用 Compose Multiplatform 实现 Android、iOS、Desktop (JVM) 和 Web (Wasm/JS) 上一致的代码高亮效果
- **28+ 编程语言支持**：内置支持 Kotlin、Java、Swift、Python、JavaScript、TypeScript、Ruby、PHP、Dart、Scala、Go、Rust、C、C++、R、SQL、JSON、YAML、TOML、Dockerfile、Bash、Lua、Haskell、Elixir、XML、HTML、CSS、Markdown 等
- **增量高亮**：基于 AST 的增量解析和高亮，适用于流式场景（如 AI 对话、实时编辑）的高效更新
- **丰富主题**：内置 4 套主题，包括 One Dark Pro、GitHub Light、Dracula Pro 和 Solarized Light，支持自定义主题
- **完整代码块组件**：完整的代码块渲染，包含行号、起始行号、指定行高亮、Diff 模式、复制按钮、语言标签、可折叠/展开、流式光标动画等功能
- **Markdown 代码块解析器**：内置 Markdown 代码围栏解析器
- **自定义词法分析器支持**：可扩展的词法分析器 API，支持添加更多编程语言
- **Token 点击交互**：单个代码 Token 的点击回调，用于实现交互功能
- **明暗主题支持**：内置明暗主题，支持显式选择和自定义主题

## 📚 支持的语言 (28+)

<details>
<summary><b>系统语言</b> — Kotlin、Java、Swift</summary>

- **Kotlin**：关键字、字符串（包括三引号字符串和模板字符串）、注释、注解、内置类型、数字
- **Java**：关键字、字符串、注释、注解、内置类型、数字
- **Swift**：关键字、字符串、注释、内置类型、数字
</details>

<details>
<summary><b>脚本语言</b> — Python、JavaScript、TypeScript、Ruby、PHP、Dart、Scala、Lua、Haskell、Elixir</summary>

- **Python**：关键字、字符串（单引号/双引号/三引号）、注释、装饰器、内置函数
- **JavaScript**：关键字、字符串、注释、正则表达式、模板字面量
- **TypeScript**：所有 JavaScript 特性加上 TypeScript 特定语法（类型、接口、泛型等）
- **Ruby**：关键字、字符串、注释、实例变量、Rails 常用内置方法
- **PHP**：`<?php` 标签、关键字、字符串、注释、`$variable` 变量、`->`/`::` 运算符
- **Dart**：关键字、字符串、注解、Flutter 常见类型与 `setState`
- **Scala**：关键字、字符串、注解、`case class`、`trait`、`object`
- **Lua**：关键字、字符串、注释、`local` 变量、`require`/`print`
- **Haskell**：关键字、字符串、注释、类型签名、代数数据类型
- **Elixir**：关键字、字符串、管道操作符、模块定义、常见内置调用
</details>

<details>
<summary><b>系统/底层语言</b> — Go、Rust、C、C++</summary>

- **Go**：关键字、字符串、注释、内置类型
- **Rust**：关键字、字符串、注释、属性、宏
- **C/C++**：关键字、字符串、注释、预处理指令
</details>

<details>
<summary><b>数据/配置语言</b> — R、SQL、JSON、YAML、TOML、Dockerfile</summary>

- **R**：赋值操作符、函数定义、`library`、`data.frame`、常用数据分析内置调用
- **SQL**：关键字、字符串、注释、标识符
- **JSON**：字符串、数字、布尔值、null、对象、数组
- **YAML**：键、值、字符串、注释、列表、字典
- **TOML**：节、数组节、键值对、字符串、布尔值
- **Dockerfile**：构建指令、环境变量、字符串、注释
</details>

<details>
<summary><b>标记/样式语言</b> — Bash、XML、HTML、CSS、Markdown</summary>

- **Bash/Shell**：关键字、字符串、注释、变量、命令
- **XML/HTML**：标签、属性、字符串、注释、实体
- **CSS**：选择器、属性、值、注释、at-rules
- **Markdown**：标题、列表、链接、代码段、块引用等
</details>

## 🎨 内置主题 (4 套)

| 主题 | 类型 | 说明 |
|-------|------|------|
| **OneDarkPro** | 暗色 | 基于 Atom 热门的 One Dark Pro 主题 |
| **GithubLight** | 亮色 | 基于 GitHub 的代码高亮配色 |
| **DraculaPro** | 暗色 | 基于 Dracula Pro 配色方案 |
| **SolarizedLight** | 亮色 | 基于 Ethan Schoonover 的 Solarized Light |

## 🛠️ 使用说明

### 基本代码块

在 Compose Multiplatform 项目中，你可以直接使用 `CodeBlock` 组件：

```kotlin
import com.hrm.codehigh.renderer.CodeBlock
import com.hrm.codehigh.theme.OneDarkProTheme

@Composable
fun MyScreen() {
    CodeBlock(
        code = """
            fun main() {
                println("Hello, CodeHigh!")
            }
        """.trimIndent(),
        language = "kotlin",
        theme = OneDarkProTheme,
        showLineNumbers = true,
        showCopyButton = true
    )
}
```

### 行内代码

用于文本中的行内代码高亮：

```kotlin
import com.hrm.codehigh.renderer.InlineCode

@Composable
fun MyText() {
    InlineCode(
        text = "val x = 42"
    )
}
```

### 测量行内代码尺寸

在需要预先占位或调整布局时，可以使用测量 API 计算行内代码的宽高：

```kotlin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import com.hrm.codehigh.renderer.measureInlineCodeSize
import com.hrm.codehigh.theme.OneDarkProTheme

@Composable
fun MeasureExample() {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val theme = OneDarkProTheme
    
    val size = remember {
        measureInlineCodeSize(
            text = "val x = 42",
            language = "kotlin",
            theme = theme,
            density = density,
            textMeasurer = textMeasurer
        )
    }
    
    // 使用测量结果进行布局
    Box(
        Modifier
            .width(size.widthDp(density).dp)
            .height(size.heightDp(density).dp)
    ) {
        // 占位或其他内容
    }
}
```

### 流式模式（适用于 AI 对话/实时输出）

对于流式场景（如 AI 聊天机器人），启用 `isStreaming` 标志以在末尾显示闪烁的光标动画：

```kotlin
import com.hrm.codehigh.renderer.CodeBlock

@Composable
fun StreamingCode() {
    var code by remember { mutableStateOf("") }
    
    CodeBlock(
        code = code,
        language = "python",
        isStreaming = true // 在末尾显示闪烁光标
    )
}
```

### 可折叠代码块

对于长代码块，使用 `maxVisibleLines` 使其可折叠：

```kotlin
CodeBlock(
    code = longCode,
    language = "java",
    maxVisibleLines = 20 // 超过 20 行时折叠
)
```

### 自定义主题

你可以通过实现 `CodeTheme` 接口来创建自定义主题：

```kotlin
import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType
import com.hrm.codehigh.theme.CodeTheme

object MyCustomTheme : CodeTheme {
    override val background = Color(0xFF1E1E1E)
    override val isDark = true
    
    override fun colorFor(type: TokenType): Color {
        return when (type) {
            TokenType.KEYWORD -> Color(0xFF569CD6)
            TokenType.STRING -> Color(0xFFCE9178)
            TokenType.COMMENT -> Color(0xFF6A9955)
            TokenType.NUMBER -> Color(0xFFB5CEA8)
            // ... 其他 token 类型
            else -> Color(0xFFD4D4D4)
        }
    }
}
```

### 使用 LocalCodeTheme 全局主题

使用 `CompositionLocal` 全局提供主题：

```kotlin
import com.hrm.codehigh.theme.LocalCodeTheme
import com.hrm.codehigh.theme.GithubLightTheme

@Composable
fun App() {
    CompositionLocalProvider(LocalCodeTheme provides GithubLightTheme) {
        // 此作用域中的所有 CodeBlock 都将使用 GithubLightTheme
        CodeBlock(code = "...", language = "kotlin")
    }
}
```

### 注册自定义词法分析器

你可以为其他语言注册自定义词法分析器：

```kotlin
import com.hrm.codehigh.lexer.LanguageRegistry
import com.hrm.codehigh.lexer.Lexer

// 注册你的自定义词法分析器
LanguageRegistry.register("my-language", MyCustomLexer)
LanguageRegistry.registerAlias("ml", "my-language") // 添加别名

// 在 CodeBlock 中使用
CodeBlock(
    code = myCode,
    language = "my-language" // 或者通过别名 "ml"
)
```

## 🏗️ 项目结构

- `:codehighlight`：核心 SDK 模块，包含词法分析器、解析器、渲染器、主题和增量高亮。
- `:codehighlight-preview`：预览组件和示例数据集。
- `:composeApp`：跨平台演示应用。
- `:androidApp`：Android 演示应用。
- `:iosApp`：iOS 应用入口模块。

## 🚀 快速开始

### 运行演示应用

- **Android**：`./gradlew :androidApp:assembleDebug`
- **Desktop**：`./gradlew :composeApp:run`
- **Web (Wasm)**：`./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
- **iOS**：在 Xcode 中打开 `iosApp/iosApp.xcworkspace` 运行。

### 运行测试

```bash
./gradlew test
```

## 📊 路线图与覆盖范围

有关支持功能的详细列表，请参阅：[HIGHLIGHTER_COVERAGE_ANALYSIS.md](./HIGHLIGHTER_COVERAGE_ANALYSIS.md)

## 💡 推荐项目

- [LaTeX](https://github.com/huarangmeng/LaTeX) — 由同一作者开发的 Kotlin Multiplatform LaTeX 解析和渲染库。如果你的项目同时需要代码高亮和 LaTeX 渲染，不妨看看！

## 📄 许可证

本项目采用 MIT 许可证 - 有关详细信息，请参阅 [LICENSE](LICENSE) 文件。

```
MIT License

Copyright (c) 2026 huarangmeng

特此免费授予任何获得本软件和相关文档文件（"软件"）副本的人不受限制地处理软件的权利，包括但不限于使用、复制、修改、合并、发布、分发、再许可和/或销售软件副本的权利，并允许向其提供软件的人这样做，符合以下条件：

上述版权声明和本许可声明应包含在软件的所有副本或主要部分中。

本软件按"原样"提供，不提供任何形式的明示或暗示保证，包括但不限于对适销性、特定用途适用性和非侵权性的保证。在任何情况下，作者或版权持有人均不对因软件或软件使用或其他交易引起的任何索赔、损害或其他责任承担责任，无论是合同诉讼、侵权诉讼还是其他形式的诉讼。
```
