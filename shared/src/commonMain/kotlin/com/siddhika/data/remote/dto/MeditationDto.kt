package com.siddhika.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MeditationDto(
    val id: Long = 0,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val category: String,
    val imageUrl: String? = null,
    val audioUrl: String? = null
)
