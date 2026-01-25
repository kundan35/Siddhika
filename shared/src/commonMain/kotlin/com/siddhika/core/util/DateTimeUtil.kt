package com.siddhika.core.util

import kotlinx.datetime.*

object DateTimeUtil {

    fun now(): Instant = Clock.System.now()

    fun nowMillis(): Long = now().toEpochMilliseconds()

    fun today(): LocalDate = now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun currentTime(): LocalTime = now().toLocalDateTime(TimeZone.currentSystemDefault()).time

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}"
    }

    fun formatTimeWithHours(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        return if (hours > 0) {
            "${hours}:${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}"
        } else {
            formatTime(seconds)
        }
    }

    fun formatDate(instant: Instant): String {
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${dateTime.dayOfMonth} ${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.year}"
    }

    fun formatDateTime(instant: Instant): String {
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val time = formatLocalTime(dateTime.time)
        return "${formatDate(instant)} at $time"
    }

    fun formatLocalTime(time: LocalTime): String {
        val hour = if (time.hour > 12) time.hour - 12 else if (time.hour == 0) 12 else time.hour
        val amPm = if (time.hour >= 12) "PM" else "AM"
        return "$hour:${time.minute.toString().padStart(2, '0')} $amPm"
    }

    fun parseTime(timeString: String): LocalTime? {
        return try {
            LocalTime.parse(timeString)
        } catch (e: Exception) {
            null
        }
    }

    fun millisToInstant(millis: Long): Instant = Instant.fromEpochMilliseconds(millis)

    fun getDayOfWeekName(dayOfWeek: DayOfWeek): String {
        return dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    }

    fun parseDaysOfWeek(daysString: String): List<DayOfWeek> {
        if (daysString.isBlank()) return emptyList()
        return daysString.split(",").mapNotNull { day ->
            try {
                DayOfWeek.valueOf(day.trim().uppercase())
            } catch (e: Exception) {
                null
            }
        }
    }

    fun encodeDaysOfWeek(days: List<DayOfWeek>): String {
        return days.joinToString(",") { it.name }
    }
}
