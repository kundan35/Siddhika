package com.siddhika.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ScriptureDto(
    val id: Long = 0,
    val title: String,
    val description: String,
    val content: String,
    val category: String,
    val language: String = "en",
    val totalChapters: Int = 1
)
