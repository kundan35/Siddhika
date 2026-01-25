package com.siddhika.data.mapper

import com.siddhika.core.util.DateTimeUtil
import com.siddhika.data.local.database.BookmarkEntity
import com.siddhika.data.local.database.MeditationEntity
import com.siddhika.data.local.database.MeditationSessionEntity
import com.siddhika.data.local.database.PrayerEntity
import com.siddhika.data.local.database.PrayerReminderEntity
import com.siddhika.data.local.database.QuoteEntity
import com.siddhika.data.local.database.ScriptureEntity
import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.model.Meditation
import com.siddhika.domain.model.MeditationSession
import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.model.Quote
import com.siddhika.domain.model.Scripture
import kotlinx.datetime.LocalTime

fun QuoteEntity.toDomain(): Quote = Quote(
    id = id,
    text = text,
    author = author,
    source = source,
    category = category,
    isFavorite = isFavorite == 1L,
    createdAt = DateTimeUtil.millisToInstant(createdAt)
)

fun MeditationEntity.toDomain(): Meditation = Meditation(
    id = id,
    title = title,
    description = description,
    durationMinutes = durationMinutes.toInt(),
    category = category,
    imageUrl = imageUrl,
    audioUrl = audioUrl
)

fun MeditationSessionEntity.toDomain(): MeditationSession = MeditationSession(
    id = id,
    meditationId = meditationId,
    completedAt = DateTimeUtil.millisToInstant(completedAt),
    durationSeconds = durationSeconds.toInt()
)

fun PrayerEntity.toDomain(): Prayer = Prayer(
    id = id,
    title = title,
    content = content,
    category = category,
    language = language
)

fun PrayerReminderEntity.toDomain(): PrayerReminder = PrayerReminder(
    id = id,
    prayerId = prayerId,
    title = title,
    time = DateTimeUtil.parseTime(time) ?: LocalTime(8, 0),
    daysOfWeek = DateTimeUtil.parseDaysOfWeek(daysOfWeek),
    isEnabled = isEnabled == 1L
)

fun ScriptureEntity.toDomain(): Scripture = Scripture(
    id = id,
    title = title,
    description = description,
    content = content,
    category = category,
    language = language,
    totalChapters = totalChapters.toInt()
)

fun BookmarkEntity.toDomain(): Bookmark = Bookmark(
    id = id,
    scriptureId = scriptureId,
    chapterNumber = chapterNumber.toInt(),
    verseNumber = verseNumber?.toInt(),
    note = note,
    createdAt = DateTimeUtil.millisToInstant(createdAt)
)
