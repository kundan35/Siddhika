package com.siddhika.domain.model

import kotlinx.datetime.Instant

data class Bookmark(
    val id: Long = 0,
    val scriptureId: Long,
    val chapterNumber: Int,
    val verseNumber: Int? = null,
    val note: String? = null,
    val createdAt: Instant
)
