package com.hrm.codehigh.preview.data

internal data class PreviewLanguage(
    val name: String,
    val languageId: String,
    val code: String
)

internal object PreviewLanguages {
    val all = listOf(
        PreviewLanguage("Kotlin", "kotlin", SampleCode.KOTLIN),
        PreviewLanguage("Java", "java", SampleCode.JAVA),
        PreviewLanguage("Swift", "swift", SampleCode.SWIFT),
        PreviewLanguage("Python", "python", SampleCode.PYTHON),
        PreviewLanguage("JavaScript", "javascript", SampleCode.JAVASCRIPT),
        PreviewLanguage("TypeScript", "typescript", SampleCode.TYPESCRIPT),
        PreviewLanguage("Ruby", "ruby", SampleCode.RUBY),
        PreviewLanguage("PHP", "php", SampleCode.PHP),
        PreviewLanguage("Dart", "dart", SampleCode.DART),
        PreviewLanguage("Scala", "scala", SampleCode.SCALA),
        PreviewLanguage("Go", "go", SampleCode.GO),
        PreviewLanguage("Rust", "rust", SampleCode.RUST),
        PreviewLanguage("C", "c", SampleCode.C),
        PreviewLanguage("C++", "cpp", SampleCode.CPP),
        PreviewLanguage("R", "r", SampleCode.R),
        PreviewLanguage("SQL", "sql", SampleCode.SQL),
        PreviewLanguage("JSON", "json", SampleCode.JSON),
        PreviewLanguage("YAML", "yaml", SampleCode.YAML),
        PreviewLanguage("TOML", "toml", SampleCode.TOML),
        PreviewLanguage("Dockerfile", "dockerfile", SampleCode.DOCKERFILE),
        PreviewLanguage("Bash", "bash", SampleCode.BASH),
        PreviewLanguage("Lua", "lua", SampleCode.LUA),
        PreviewLanguage("Haskell", "haskell", SampleCode.HASKELL),
        PreviewLanguage("Elixir", "elixir", SampleCode.ELIXIR),
        PreviewLanguage("Diff", "diff", SampleCode.DIFF),
        PreviewLanguage("HTML", "html", SampleCode.HTML),
        PreviewLanguage("XML", "xml", SampleCode.XML),
        PreviewLanguage("CSS", "css", SampleCode.CSS)
    )
}
