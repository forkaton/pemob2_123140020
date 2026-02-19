package com.forkaton.pemob2_123140020

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        val viewModel: NewsViewModel = viewModel { NewsViewModel() }
        NewsAppScreen(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppScreen(viewModel: NewsViewModel) {
    val newsList by viewModel.newsList.collectAsState()
    val readCount by viewModel.readCount.collectAsState()
    val currentCategory by viewModel.currentCategory.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var detailContent by remember { mutableStateOf("Memuat...") }
    var selectedNewsTitle by remember { mutableStateOf("") }

    val categories = listOf("Semua", "Teknologi", "Olahraga", "Politik", "Hiburan")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Feed", fontWeight = FontWeight.Bold) },
                actions = {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = "Dibaca: $readCount",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    FilterChip(
                        selected = category == currentCategory,
                        onClick = { viewModel.setCategory(category) },
                        label = { Text(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(newsList) { news ->
                    NewsCard(news = news) {
                        viewModel.markNewsAsRead()
                        selectedNewsTitle = news.title
                        detailContent = "Memuat detail berita secara asinkron..."
                        showBottomSheet = true

                        viewModel.getNewsDetail(news.id) { result ->
                            detailContent = result
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = selectedNewsTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = detailContent,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Tutup")
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
    // Solusi UX: Menggunakan Emoji sebagai pengganti Icon agar lebih ringan & anti error
    val emoji = when (news.category) {
        "Teknologi" -> "💻"
        "Olahraga" -> "⚽"
        "Politik" -> "⚖️"
        "Hiburan" -> "🎬"
        "Error" -> "⚠️"
        else -> "📰"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (news.category == "Error") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = emoji, style = MaterialTheme.typography.headlineSmall)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kategori: ${news.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}