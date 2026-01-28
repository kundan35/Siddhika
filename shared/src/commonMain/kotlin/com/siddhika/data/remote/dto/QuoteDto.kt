package com.siddhika.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
    val id: Long = 0,
    val text: String,
    val author: String,
    val source: String? = null,
    val category: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = 0
)
