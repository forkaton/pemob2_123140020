package com.forkaton.pemob2_123140020

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NewsRepositoryTest {

    private val repository = NewsRepository()

    @Test
    fun testFetchNewsDetailAsync() = runTest {
        // Menguji Coroutines (async/await)
        val detail = repository.fetchNewsDetailAsync(1)
        assertNotNull(detail)
        assertTrue(detail.contains("ID: 1"))
    }

    @Test
    fun testGetNewsFeedFlow() = runTest {
        // Menguji Flow emission dan Error Handling (.catch)
        val newsList = repository.getNewsFeed()
            .catch { emit(News(-1, "Error Teratasi", "Error")) }
            .take(2) // Ambil 2 emisi pertama saja agar test tidak berjalan selamanya
            .toList()

        assertTrue(newsList.isNotEmpty())
        assertEquals(2, newsList.size)
    }
}