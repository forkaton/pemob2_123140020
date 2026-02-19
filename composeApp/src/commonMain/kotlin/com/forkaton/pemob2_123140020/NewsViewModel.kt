package com.forkaton.pemob2_123140020

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val _currentCategory = MutableStateFlow("Semua")
    val currentCategory: StateFlow<String> = _currentCategory.asStateFlow()

    init {
        observeNewsFeed()
    }

    private fun observeNewsFeed() {
        viewModelScope.launch {
            // KUNCI UTAMA: Loop ini akan otomatis me-restart Flow jika terputus karena error!
            while (true) {
                repository.getNewsFeed()
                    .retryWhen { _, attempt ->
                        if (attempt < 3) {
                            delay(1000)
                            true
                        } else false
                    }
                    .catch { e ->
                        // Menangkap error, menampilkannya, tapi aplikasi tidak akan mati permanen
                        _newsList.value = listOf(News(-1, "Error Jaringan: ${e.message}", "Error")) + _newsList.value
                    }
                    .filter { news ->
                        _currentCategory.value == "Semua" || news.category == _currentCategory.value
                    }
                    .map { news ->
                        news.copy(title = "Info: ${news.category.uppercase()} - ${news.title}")
                    }
                    .onEach { news ->
                        println("Berita siap ditampilkan: ${news.title}")
                    }
                    .collect { news ->
                        _newsList.value = listOf(news) + _newsList.value
                    }

                // Jika Flow mati karena error, tunggu 2 detik lalu sistem otomatis mengambil berita lagi
                delay(2000)
            }
        }
    }

    fun setCategory(category: String) {
        _currentCategory.value = category
        _newsList.value = emptyList()
    }

    fun markNewsAsRead() {
        _readCount.value += 1
    }

    fun getNewsDetail(newsId: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val detail = repository.fetchNewsDetailAsync(newsId)
                onResult(detail)
            } catch (e: Exception) {
                onResult("Gagal memuat detail: Terjadi kesalahan sistem.")
            }
        }
    }
}