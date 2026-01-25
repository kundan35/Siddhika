package com.siddhika.ui.screens.prayers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.usecase.prayer.GetPrayerRemindersUseCase
import com.siddhika.domain.usecase.prayer.GetPrayersUseCase
import com.siddhika.domain.usecase.prayer.SetPrayerReminderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

class PrayerViewModel(
    getPrayersUseCase: GetPrayersUseCase,
    private val getPrayerRemindersUseCase: GetPrayerRemindersUseCase
) : ScreenModel {

    val prayers: StateFlow<List<Prayer>> = getPrayersUseCase()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val reminders: StateFlow<List<PrayerReminder>> = getPrayerRemindersUseCase()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleReminder(id: Long, enabled: Boolean) {
        screenModelScope.launch {
            getPrayerRemindersUseCase.toggleEnabled(id, enabled)
        }
    }

    fun deleteReminder(id: Long) {
        screenModelScope.launch {
            getPrayerRemindersUseCase.delete(id)
        }
    }
}

class PrayerDetailViewModel(
    private val prayerId: Long,
    private val getPrayersUseCase: GetPrayersUseCase
) : ScreenModel {

    private val _prayer = MutableStateFlow<Prayer?>(null)
    val prayer: StateFlow<Prayer?> = _prayer.asStateFlow()

    init {
        loadPrayer()
    }

    private fun loadPrayer() {
        screenModelScope.launch {
            _prayer.value = getPrayersUseCase.byId(prayerId)
        }
    }
}

class AddReminderViewModel(
    private val setPrayerReminderUseCase: SetPrayerReminderUseCase
) : ScreenModel {

    fun saveReminder(
        title: String,
        time: LocalTime,
        daysOfWeek: List<DayOfWeek>,
        prayerId: Long?
    ) {
        screenModelScope.launch {
            setPrayerReminderUseCase(
                title = title,
                time = time,
                daysOfWeek = daysOfWeek,
                prayerId = prayerId
            )
        }
    }
}
