package com.hrm.codehigh.preview.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.preview.data.PreviewLanguage
import com.hrm.codehigh.preview.data.PreviewLanguages
import com.hrm.codehigh.renderer.CodeBlock

@Composable
internal fun LanguageCategory() {
    var previewLanguage by remember { mutableStateOf<PreviewLanguage?>(null) }

    if (previewLanguage == null) {
        LanguageSelectionPage(
            onLanguageSelected = { previewLanguage = it }
        )
    } else {
        LanguagePreviewPage(
            language = previewLanguage!!,
            onBack = { previewLanguage = null }
        )
    }
}

@Composable
private fun LanguageSelectionPage(
    onLanguageSelected: (PreviewLanguage) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "语言高亮",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "点击语言卡片后进入下一页查看代码预览。",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LanguageSelectorGrid(
            onLanguageSelected = onLanguageSelected,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun LanguagePreviewPage(
    language: PreviewLanguage,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(onClick = onBack) {
                Text("返回")
            }
            Text(
                text = "代码预览",
                style = MaterialTheme.typography.titleLarge
            )
        }

        CodeBlock(
            code = language.code,
            language = language.languageId,
            showLineNumbers = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LanguageSelectorGrid(
    onLanguageSelected: (PreviewLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(PreviewLanguages.all) { language ->
            FilterChip(
                selected = false,
                onClick = { onLanguageSelected(language) },
                label = {
                    Text(language.name)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
