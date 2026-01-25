package com.siddhika.domain.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

data class PrayerReminder(
    val id: Long = 0,
    val prayerId: Long? = null,
    val title: String,
    val time: LocalTime,
    val daysOfWeek: List<DayOfWeek>,
    val isEnabled: Boolean = true
)
