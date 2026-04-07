# CodeHigh

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.10.0-brightgreen.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android API](https://img.shields.io/badge/Android%20API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A high-performance cross-platform code highlighting library developed based on Kotlin Multiplatform (KMP) and Compose Multiplatform. It supports consistent rendering effects on Android, iOS, Desktop (JVM), and Web (Wasm/JS) platforms.

[中文版本](./README_zh.md)

## 🌟 Key Features

- **Multi-platform Consistency**: Uses Compose Multiplatform for consistent code highlighting on Android, iOS, Desktop (JVM), and Web (Wasm/JS).
- **28+ Programming Languages**: Built-in support for Kotlin, Java, Swift, Python, JavaScript, TypeScript, Ruby, PHP, Dart, Scala, Go, Rust, C, C++, R, SQL, JSON, YAML, TOML, Dockerfile, Bash, Lua, Haskell, Elixir, XML, HTML, CSS, Markdown, and more.
- **Incremental Highlighting**: AST-based incremental parsing and highlighting for efficient updates in streaming scenarios (e.g., AI chat, real-time editing).
- **Rich Themes**: 4 built-in themes including One Dark Pro, GitHub Light, Dracula Pro, and Solarized Light, with custom theme support.
- **Code Block Components**: Complete code block rendering with line numbers, copy button, language label, collapsible/expandable, and streaming cursor animation.
- **Markdown Fence Parser**: Built-in parser for Markdown code fence blocks.
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
<summary><b>Markup/Style Languages</b> — Bash, XML, HTML, CSS, Markdown</summary>

- **Bash/Shell**: keywords, strings, comments, variables, commands
- **XML/HTML**: tags, attributes, strings, comments, entities
- **CSS**: selectors, properties, values, comments, at-rules
- **Markdown**: headers, lists, links, code spans, block quotes, etc.
</details>

## 🎨 Built-in Themes (4)

| Theme | Type | Description |
|-------|------|-------------|
| **OneDarkPro** | Dark | Based on Atom's popular One Dark Pro theme |
| **GithubLight** | Light | Based on GitHub's code highlighting colors |
| **DraculaPro** | Dark | Based on the Dracula Pro color scheme |
| **SolarizedLight** | Light | Based on Ethan Schoonover's Solarized Light |

## 🛠️ Usage

### Basic Code Block

In a Compose Multiplatform project, you can use the `CodeBlock` component directly:

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

### Inline Code

For inline code highlighting within text:

```kotlin
import com.hrm.codehigh.renderer.InlineCode

@Composable
fun MyText() {
    InlineCode(
        text = "val x = 42"
    )
}
```

### Measuring Inline Code Size

When you need to pre-occupy space or adjust layout, use the measurement API to calculate the width and height of inline code:

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
    
    // Use measurement result for layout
    Box(
        Modifier
            .width(size.widthDp(density).dp)
            .height(size.heightDp(density).dp)
    ) {
        // Placeholder or other content
    }
}
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

### Custom Theme

You can create custom themes by implementing the `CodeTheme` interface:

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
            // ... other token types
            else -> Color(0xFFD4D4D4)
        }
    }
}
```

### Using LocalCodeTheme for Global Theming

Use `CompositionLocal` to provide a theme globally:

```kotlin
import com.hrm.codehigh.theme.LocalCodeTheme
import com.hrm.codehigh.theme.GithubLightTheme

@Composable
fun App() {
    CompositionLocalProvider(LocalCodeTheme provides GithubLightTheme) {
        // All CodeBlocks in this scope will use GithubLightTheme
        CodeBlock(code = "...", language = "kotlin")
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

- `:codehighlight`: Core SDK module, containing lexers, parser, renderer, themes, and incremental highlighting.
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
