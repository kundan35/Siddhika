package com.siddhika.domain.model

data class Prayer(
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String,
    val language: String = "en"
)
