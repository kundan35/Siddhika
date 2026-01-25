package com.siddhika.domain.repository

import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.PrayerReminder
import kotlinx.coroutines.flow.Flow

interface PrayerRepository {
    fun getAllPrayers(): Flow<List<Prayer>>
    fun getPrayersByCategory(category: String): Flow<List<Prayer>>
    suspend fun getPrayerById(id: Long): Prayer?
    suspend fun addPrayer(prayer: Prayer)

    fun getAllReminders(): Flow<List<PrayerReminder>>
    fun getEnabledReminders(): Flow<List<PrayerReminder>>
    suspend fun getReminderById(id: Long): PrayerReminder?
    suspend fun addReminder(reminder: PrayerReminder): Long
    suspend fun updateReminderEnabled(id: Long, isEnabled: Boolean)
    suspend fun deleteReminder(id: Long)
}
