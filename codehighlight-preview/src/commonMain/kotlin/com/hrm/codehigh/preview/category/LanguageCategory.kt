package com.hrm.codehigh.preview.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock

/**
 * 语言高亮分类，展示所有支持语言的代码高亮效果。
 */
@Composable
internal fun LanguageCategory() {
    val languages = listOf(
        "Kotlin" to SampleCode.KOTLIN,
        "Java" to SampleCode.JAVA,
        "Swift" to SampleCode.SWIFT,
        "Python" to SampleCode.PYTHON,
        "JavaScript" to SampleCode.JAVASCRIPT,
        "TypeScript" to SampleCode.TYPESCRIPT,
        "Ruby" to SampleCode.RUBY,
        "PHP" to SampleCode.PHP,
        "Dart" to SampleCode.DART,
        "Scala" to SampleCode.SCALA,
        "Go" to SampleCode.GO,
        "Rust" to SampleCode.RUST,
        "C" to SampleCode.C,
        "C++" to SampleCode.CPP,
        "R" to SampleCode.R,
        "SQL" to SampleCode.SQL,
        "JSON" to SampleCode.JSON,
        "YAML" to SampleCode.YAML,
        "TOML" to SampleCode.TOML,
        "Dockerfile" to SampleCode.DOCKERFILE,
        "Bash" to SampleCode.BASH,
        "Lua" to SampleCode.LUA,
        "Haskell" to SampleCode.HASKELL,
        "Elixir" to SampleCode.ELIXIR,
        "HTML" to SampleCode.HTML,
        "XML" to SampleCode.XML,
        "CSS" to SampleCode.CSS,
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(languages) { (name, code) ->
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CodeBlock(
                    code = code,
                    language = name.lowercase().replace("+", "p").replace(" ", ""),
                    showLineNumbers = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
