package com.siddhika.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PrayerDto(
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String,
    val language: String = "en"
)
