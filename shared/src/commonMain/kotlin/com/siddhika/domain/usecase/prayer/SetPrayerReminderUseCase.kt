package com.siddhika.domain.usecase.prayer

import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.repository.PrayerRepository
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

class SetPrayerReminderUseCase(
    private val repository: PrayerRepository
) {
    suspend operator fun invoke(
        title: String,
        time: LocalTime,
        daysOfWeek: List<DayOfWeek>,
        prayerId: Long? = null
    ): Long {
        val reminder = PrayerReminder(
            prayerId = prayerId,
            title = title,
            time = time,
            daysOfWeek = daysOfWeek,
            isEnabled = true
        )
        return repository.addReminder(reminder)
    }
}
