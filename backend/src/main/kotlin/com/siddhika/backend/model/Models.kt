package com.siddhika.backend.model

data class Quote(
    val id: Long,
    val text: String,
    val author: String,
    val source: String? = null,
    val category: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class Meditation(
    val id: Long,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val category: String,
    val imageUrl: String? = null,
    val audioUrl: String? = null
)

data class Prayer(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val language: String = "en"
)

data class Scripture(
    val id: Long,
    val title: String,
    val description: String,
    val content: String,
    val category: String,
    val language: String = "en",
    val totalChapters: Int = 1
)
