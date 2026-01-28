package com.siddhika.data.remote.mapper

import com.siddhika.data.remote.dto.MeditationDto
import com.siddhika.data.remote.dto.PrayerDto
import com.siddhika.data.remote.dto.QuoteDto
import com.siddhika.data.remote.dto.ScriptureDto
import com.siddhika.domain.model.Meditation
import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.Quote
import com.siddhika.domain.model.Scripture
import kotlinx.datetime.Instant

fun QuoteDto.toDomain(): Quote = Quote(
    id = id,
    text = text,
    author = author,
    source = source,
    category = category,
    isFavorite = isFavorite,
    createdAt = Instant.fromEpochMilliseconds(createdAt)
)

fun MeditationDto.toDomain(): Meditation = Meditation(
    id = id,
    title = title,
    description = description,
    durationMinutes = durationMinutes,
    category = category,
    imageUrl = imageUrl,
    audioUrl = audioUrl
)

fun PrayerDto.toDomain(): Prayer = Prayer(
    id = id,
    title = title,
    content = content,
    category = category,
    language = language
)

fun ScriptureDto.toDomain(): Scripture = Scripture(
    id = id,
    title = title,
    description = description,
    content = content,
    category = category,
    language = language,
    totalChapters = totalChapters
)
