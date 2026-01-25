package com.siddhika.domain.model

import kotlinx.datetime.Instant

data class Meditation(
    val id: Long = 0,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val category: String,
    val imageUrl: String? = null,
    val audioUrl: String? = null
)

data class MeditationSession(
    val id: Long = 0,
    val meditationId: Long,
    val completedAt: Instant,
    val durationSeconds: Int
)
