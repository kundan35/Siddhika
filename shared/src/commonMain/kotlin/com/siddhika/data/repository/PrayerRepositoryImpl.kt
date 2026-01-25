package com.siddhika.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.siddhika.core.util.DateTimeUtil
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.data.mapper.toDomain
import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.repository.PrayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PrayerRepositoryImpl(
    private val database: SiddhikaDatabase
) : PrayerRepository {

    private val queries = database.siddhikaDatabaseQueries

    override fun getAllPrayers(): Flow<List<Prayer>> =
        queries.getAllPrayers()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getPrayersByCategory(category: String): Flow<List<Prayer>> =
        queries.getPrayersByCategory(category)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getPrayerById(id: Long): Prayer? = withContext(Dispatchers.IO) {
        queries.getPrayerById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun addPrayer(prayer: Prayer) = withContext(Dispatchers.IO) {
        queries.insertPrayer(
            title = prayer.title,
            content = prayer.content,
            category = prayer.category,
            language = prayer.language
        )
    }

    override fun getAllReminders(): Flow<List<PrayerReminder>> =
        queries.getAllReminders()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getEnabledReminders(): Flow<List<PrayerReminder>> =
        queries.getEnabledReminders()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getReminderById(id: Long): PrayerReminder? = withContext(Dispatchers.IO) {
        queries.getReminderById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun addReminder(reminder: PrayerReminder): Long = withContext(Dispatchers.IO) {
        queries.insertReminder(
            prayerId = reminder.prayerId,
            title = reminder.title,
            time = reminder.time.toString(),
            daysOfWeek = DateTimeUtil.encodeDaysOfWeek(reminder.daysOfWeek),
            isEnabled = if (reminder.isEnabled) 1L else 0L
        )
        // Return the last inserted row id
        queries.getReminderById(
            queries.getAllReminders().executeAsList().lastOrNull()?.id ?: 0L
        ).executeAsOneOrNull()?.id ?: 0L
    }

    override suspend fun updateReminderEnabled(id: Long, isEnabled: Boolean) = withContext(Dispatchers.IO) {
        queries.updateReminderEnabled(if (isEnabled) 1L else 0L, id)
    }

    override suspend fun deleteReminder(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteReminder(id)
    }
}
