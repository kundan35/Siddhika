package com.siddhika.domain.model

import kotlinx.datetime.Instant

data class Quote(
    val id: Long = 0,
    val text: String,
    val author: String,
    val source: String? = null,
    val category: String,
    val isFavorite: Boolean = false,
    val createdAt: Instant
)
