package com.siddhika.domain.usecase.prayer

import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.repository.PrayerRepository
import kotlinx.coroutines.flow.Flow

class GetPrayerRemindersUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): Flow<List<PrayerReminder>> = repository.getAllReminders()

    fun enabled(): Flow<List<PrayerReminder>> = repository.getEnabledReminders()

    suspend fun toggleEnabled(id: Long, isEnabled: Boolean) {
        repository.updateReminderEnabled(id, isEnabled)
    }

    suspend fun delete(id: Long) {
        repository.deleteReminder(id)
    }
}
