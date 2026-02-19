package com.forkaton.pemob2_123140020

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
// Baris import Preview sudah dihapus

@Composable
// Baris @Preview sudah dihapus
fun App() {
    MaterialTheme {
        val viewModel: NewsViewModel = viewModel { NewsViewModel() }
        NewsAppScreen(viewModel)
    }
}

@Composable
fun NewsAppScreen(viewModel: NewsViewModel) {
    val newsList by viewModel.newsList.collectAsState()
    val readCount by viewModel.readCount.collectAsState()
    val currentCategory by viewModel.currentCategory.collectAsState()

    var detailMessage by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Semua", "Teknologi", "Olahraga", "Politik", "Hiburan")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Total Dibaca: $readCount berita",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                if (category == currentCategory) {
                    Button(onClick = { viewModel.setCategory(category) }) {
                        Text(category)
                    }
                } else {
                    OutlinedButton(onClick = { viewModel.setCategory(category) }) {
                        Text(category)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        detailMessage?.let {
            Text(text = "Info: $it", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 8.dp))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(newsList) { news ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.markNewsAsRead()
                            detailMessage = "Memuat detail..."

                            viewModel.getNewsDetail(news.id) { detailText ->
                                detailMessage = detailText
                            }
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = news.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Kategori: ${news.category}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}