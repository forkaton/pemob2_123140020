package com.forkaton.pemob2_123140020

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

class NewsRepository {
    private val categories = listOf("Teknologi", "Olahraga", "Politik", "Hiburan")
    private var newsIdCounter = 1

    // Syarat 1: Menggunakan flow {} builder
    fun getNewsFeed(): Flow<News> = flow {
        while (true) {
            delay(2000)
            // Bonus: Simulasi Error Jaringan
            if (Random.nextInt(100) < 15) {
                throw Exception("Koneksi terputus saat mengambil berita!")
            }
            val randomCategory = categories.random()
            val news = News(
                id = newsIdCounter++,
                title = "Berita $randomCategory ${Random.nextInt(100, 999)}",
                category = randomCategory
            )
            emit(news) // Implementasi emit benar
        }
    }

    // Syarat 5: Coroutines Usage (async/await, dispatchers benar)
    suspend fun fetchNewsDetailAsync(newsId: Int): String = withContext(Dispatchers.Default) {
        val deferredDetail = async {
            delay(1500)
            "Ini adalah isi detail lengkap dari berita dengan ID: $newsId. Data ini diambil secara asinkron."
        }
        deferredDetail.await()
    }
}