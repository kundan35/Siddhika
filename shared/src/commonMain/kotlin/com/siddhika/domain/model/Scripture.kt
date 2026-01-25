package com.siddhika.domain.model

data class Scripture(
    val id: Long = 0,
    val title: String,
    val description: String,
    val content: String,
    val category: String,
    val language: String = "en",
    val totalChapters: Int = 1
)
