# CodeHigh

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.10.0-brightgreen.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.huarangmeng/codehighlight-render.svg?color=orange&label=Maven%20Central)](https://central.sonatype.com/search?q=io.github.huarangmeng%3Acodehighlight-render)
[![Android API](https://img.shields.io/badge/Android%20API-23%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A high-performance cross-platform code highlighting library developed based on Kotlin Multiplatform (KMP) and Compose Multiplatform. It supports consistent rendering effects on Android, iOS, macOS (Kotlin/Native), Desktop (JVM), and Web (Wasm/JS) platforms.

[中文版本](./README_zh.md)

The library modules expose an experimental `macosArm64` Kotlin/Native target. Native consumers must opt in with `org.jetbrains.compose.experimental.macos.enabled=true`; the demo application remains JVM-based. The macOS CI job runs native tests and checks the Maven variant metadata. This does not add Windows or Linux Native UI targets; those platforms continue to use JVM artifacts.

## 🌟 Key Features

- **Multi-platform Consistency**: Uses Compose Multiplatform for consistent code highlighting on Android, iOS, macOS (Kotlin/Native), Desktop (JVM), and Web (Wasm/JS).
- **28+ Programming Languages**: Built-in support for Kotlin, Java, Swift, Python, JavaScript, TypeScript, Ruby, PHP, Dart, Scala, Go, Rust, C, C++, R, SQL, JSON, YAML, TOML, Dockerfile, Bash, Lua, Haskell, Elixir, XML, HTML, CSS, Diff, and more.
- **Incremental Highlighting**: AST-based incremental parsing and highlighting for efficient updates in streaming scenarios (e.g., AI chat, real-time editing).
- **Rich Themes**: 4 built-in themes including One Dark Pro, GitHub Light, Dracula Pro, and Solarized Light, with custom theme support.
- **Code Block Components**: Complete code block rendering with line numbers, configurable start lines, highlighted lines, diff mode, copy button, language label, collapsible/expandable, and streaming cursor animation.
- **Custom Lexer Support**: Extensible lexer API for adding support for additional programming languages.
- **Token Click Interaction**: Click callback for individual code tokens for interactive features.
- **Light/Dark Themes**: Built-in light and dark themes with explicit selection and custom theme support.

## 📚 Supported Languages (28+)

<details>
<summary><b>System Languages</b> — Kotlin, Java, Swift</summary>

- **Kotlin**: keywords, strings (including triple-quoted and template strings), comments, annotations, built-in types, numbers
- **Java**: keywords, strings, comments, annotations, built-in types, numbers
- **Swift**: keywords, strings, comments, built-in types, numbers
</details>

<details>
<summary><b>Scripting Languages</b> — Python, JavaScript, TypeScript, Ruby, PHP, Dart, Scala, Lua, Haskell, Elixir</summary>

- **Python**: keywords, strings (single/double/triple quotes), comments, decorators, built-in functions
- **JavaScript**: keywords, strings, comments, regular expressions, template literals
- **TypeScript**: all JavaScript features plus TypeScript-specific syntax (types, interfaces, generics, etc.)
- **Ruby**: keywords, strings, comments, instance variables, and common Rails-oriented built-ins
- **PHP**: `<?php` tags, keywords, strings, comments, `$variable` tokens, and `->`/`::` operators
- **Dart**: keywords, strings, annotations, common Flutter types, and `setState`
- **Scala**: keywords, strings, annotations, `case class`, `trait`, and `object`
- **Lua**: keywords, strings, comments, `local` variables, and `require`/`print`
- **Haskell**: keywords, strings, comments, type signatures, and algebraic data types
- **Elixir**: keywords, strings, pipe operators, module definitions, and common built-in calls
</details>

<details>
<summary><b>System/Systems Languages</b> — Go, Rust, C, C++</summary>

- **Go**: keywords, strings, comments, built-in types
- **Rust**: keywords, strings, comments, attributes, macros
- **C/C++**: keywords, strings, comments, preprocessor directives
</details>

<details>
<summary><b>Data/Configuration Languages</b> — R, SQL, JSON, YAML, TOML, Dockerfile</summary>

- **R**: assignment operators, function definitions, `library`, `data.frame`, and common data analysis built-ins
- **SQL**: keywords, strings, comments, identifiers
- **JSON**: strings, numbers, booleans, null, objects, arrays
- **YAML**: keys, values, strings, comments, lists, dictionaries
- **TOML**: sections, array sections, key-value pairs, strings, and booleans
- **Dockerfile**: build instructions, environment variables, strings, and comments
</details>

<details>
<summary><b>Markup/Style/Diff Languages</b> — Bash, XML, HTML, CSS, Diff</summary>

- **Bash/Shell**: keywords, strings, comments, variables, commands
- **XML/HTML**: tags, attributes, strings, comments, entities
- **CSS**: selectors, properties, values, comments, at-rules
- **Diff/Patch**: file headers, hunk markers, added/removed lines, and patch metadata
</details>

## 🎨 Built-in Themes (4)

| Theme | Type | Description |
|-------|------|-------------|
| **OneDarkPro** | Dark | Based on Atom's popular One Dark Pro theme |
| **GithubLight** | Light | Based on GitHub's code highlighting colors |
| **DraculaPro** | Dark | Based on the Dracula Pro color scheme |
| **SolarizedLight** | Light | Based on Ethan Schoonover's Solarized Light |

## 📦 Installation

### Add Dependency

Add to your `gradle/libs.versions.toml`:

```toml
[versions]
codehigh = "1.1.0"

[libraries]
codehigh-render = { module = "io.github.huarangmeng:codehighlight-render", version.ref = "codehigh" }
codehigh-parser = { module = "io.github.huarangmeng:codehighlight-parser", version.ref = "codehigh" }
```

Then add it in your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.codehigh.render)
    implementation(libs.codehigh.parser)
}
```

If you do not use Version Catalog, you can add the dependency directly:

```kotlin
dependencies {
    implementation("io.github.huarangmeng:codehighlight-render:1.1.0")
    implementation("io.github.huarangmeng:codehighlight-parser:1.1.0")
}
```

Use `codehighlight-parser` directly when you only need tokenization, language registration, or incremental parsing without Compose rendering.

## 🛠️ Usage

The examples below describe the current source API; publish the upgraded renderer before using them with a release dependency.

### Basic Code Block

In a Compose Multiplatform project, you can use the `CodeBlock` component directly:

```kotlin
import com.hrm.codehigh.renderer.CodeBlock

@Composable
fun MyScreen() {
    CodeBlock(
        code = """
            fun main() {
                println("Hello, CodeHigh!")
            }
        """.trimIndent(),
        language = "kotlin",
        showLineNumbers = true,
        showCopyButton = true
    )
}
```

### Shared appearance API

`CodeBlock(code = code)` and `InlineCode(text = text)` work without configuration. Both accept the same three optional groups: `colors: CodeColors` for syntax and surfaces, `typography: CodeTypography` for text, and `layout: CodeLayout` for padding, shapes, and line-number geometry. Display switches stay direct parameters, as does the single host event, `onTokenClick`.

```kotlin
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrm.codehigh.renderer.*

val colors = CodeDefaults.oneDarkProColors()
val typography = CodeTypography(
    text = TextStyle(fontSize = 20.sp, lineHeight = 32.sp),
    inlineCode = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    title = TextStyle(fontSize = 16.sp),
)
val layout = CodeLayout(
    contentPadding = PaddingValues(16.dp),
    inlineContentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
)

CodeBlock(code = code, language = "kotlin", colors = colors, typography = typography, layout = layout)
InlineCode(text = "README.md", colors = colors, typography = typography, layout = layout)
```

Rendering defaults are preserved: One Dark Pro, 13sp/20sp body and inline text, 12dp horizontal / 8dp vertical block padding, and 6dp horizontal / 2dp vertical inline padding with a 4dp corner radius and 1dp border. Every preset retains its inline surfaces, borders, diff colors, and highlighted-line colors. Streaming, copy, folding, line numbers, and token clicks keep their behavior; `maxVisibleLines = 0` still supports initially hiding all lines.

### Measuring code

Measurement accepts the same `typography` and `layout` as painting; colors are unnecessary. `measureCodeBlockSize` returns pixel `height`, `lineHeight`, and `toolbarHeight` for a full uncollapsed block. Pass the same source, language, title, and copy-button flag; a trailing newline still represents an additional empty code line. `measureInlineCodeSize` preserves its existing size reservation including padding and border.

```kotlin
val blockSize = measureCodeBlockSize(
    code = code,
    language = "kotlin",
    typography = typography,
    layout = layout,
    density = LocalDensity.current,
    textMeasurer = rememberTextMeasurer(),
)
val inlineSize = measureInlineCodeSize(
    text = "README.md",
    typography = typography,
    layout = layout,
    density = LocalDensity.current,
    textMeasurer = rememberTextMeasurer(),
)
```

### Streaming Mode (For AI Chat/Real-time Output)

For streaming scenarios (e.g., AI chatbots), enable the `isStreaming` flag to show a blinking cursor animation at the end:

```kotlin
import com.hrm.codehigh.renderer.CodeBlock

@Composable
fun StreamingCode() {
    var code by remember { mutableStateOf("") }
    
    CodeBlock(
        code = code,
        language = "python",
        isStreaming = true // Shows blinking cursor at the end
    )
}
```

### Collapsible Code Blocks

For long code blocks, use `maxVisibleLines` to make them collapsible:

```kotlin
CodeBlock(
    code = longCode,
    language = "java",
    maxVisibleLines = 20 // Collapse after 20 lines
)
```

### Color presets and app defaults

All four original palettes retain every token color:

| Preset / 预设 | Factory / 工厂 |
|---|---|
| GitHub Light | `CodeDefaults.githubLightColors()` / `lightColors()` |
| One Dark Pro | `CodeDefaults.oneDarkProColors()` / `darkColors()` |
| Dracula Pro | `CodeDefaults.draculaProColors()` |
| Solarized Light | `CodeDefaults.solarizedLightColors()` |

`CodeColors()` is a fixed GitHub Light palette; components still default to One Dark Pro. Customize a preset with `.copy(...)`. `ProvideCodeDefaults` supplies app defaults and instance arguments take precedence. `CodeDefaults.colors(keyword = ...)` overrides only that named color and inherits the rest from the current scope.

```kotlin
import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.renderer.*

@Composable
fun App() {
    val colors = CodeDefaults.githubLightColors().copy(
        keyword = Color(0xFF569CD6),
        string = Color(0xFFCE9178),
    )
    ProvideCodeDefaults(colors = colors) {
        CodeBlock(code = "val answer = 42", language = "kotlin")
        InlineCode(text = "answer")
        CodeBlock(
            code = "println(answer)", language = "kotlin",
            colors = CodeDefaults.colors(keyword = Color.Magenta),
            onTokenClick = { token -> /* host action */ },
        )
    }
}
```

### Registering Custom Lexers

You can register custom lexers for additional languages:

```kotlin
import com.hrm.codehigh.lexer.LanguageRegistry
import com.hrm.codehigh.lexer.Lexer

// Register your custom lexer
LanguageRegistry.register("my-language", MyCustomLexer)
LanguageRegistry.registerAlias("ml", "my-language") // Add alias

// Use it in CodeBlock
CodeBlock(
    code = myCode,
    language = "my-language" // Or "ml" via alias
)
```

## 🏗️ Project Structure

- `:codehighlight-parser`: Parser SDK module, containing tokens, lexers, language registration, and incremental parsing.
- `:codehighlight-render`: Compose render SDK module, containing renderer components, themes, and parser integration.
- `:codehighlight-preview`: Preview components and sample datasets.
- `:composeApp`: Cross-platform Demo application.
- `:androidApp`: Android Demo application.
- `:iosApp`: iOS application entry module.

## 🚀 Quick Start

### Running the Demo App

- **Android**: `./gradlew :androidApp:assembleDebug`
- **Desktop**: `./gradlew :composeApp:run`
- **Web (Wasm)**: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
- **iOS**: Open `iosApp/iosApp.xcworkspace` in Xcode to run.

### Running Tests

```bash
./gradlew test
```

## 📊 Roadmap & Coverage

For a detailed list of supported features, please refer to: [HIGHLIGHTER_COVERAGE_ANALYSIS.md](./HIGHLIGHTER_COVERAGE_ANALYSIS.md)

## 💡 Recommended

- [Markdown](https://github.com/huarangmeng/Markdown) — A dedicated Kotlin Multiplatform Markdown parsing and rendering library by the same author. If you need Markdown parsing or rendering, use it alongside `codehigh`.
- [LaTeX](https://github.com/huarangmeng/LaTeX) — A Kotlin Multiplatform LaTeX parsing and rendering library by the same author. If you need both code highlighting and LaTeX rendering in your project, check it out!

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2026 huarangmeng

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
