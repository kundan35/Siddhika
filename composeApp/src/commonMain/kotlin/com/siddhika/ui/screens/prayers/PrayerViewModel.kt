package com.siddhika.ui.screens.prayers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.UiState
import com.siddhika.domain.model.Prayer
import com.siddhika.domain.model.PrayerReminder
import com.siddhika.domain.usecase.prayer.GetPrayerRemindersUseCase
import com.siddhika.domain.usecase.prayer.GetPrayersUseCase
import com.siddhika.domain.usecase.prayer.SetPrayerReminderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

class PrayerViewModel(
    private val getPrayersUseCase: GetPrayersUseCase,
    private val getPrayerRemindersUseCase: GetPrayerRemindersUseCase
) : ScreenModel {

    val prayers: StateFlow<UiState<List<Prayer>>> = getPrayersUseCase()
        .map { list ->
            if (list.isEmpty()) UiState.Empty else UiState.Success(list)
        }
        .catch { emit(UiState.Error(it.message ?: "Failed to load prayers")) }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
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

    fun retry() {
        // Flow from use case is reactive; no explicit retry needed for Room/SQLDelight flows.
    }
}

class PrayerDetailViewModel(
    private val prayerId: Long,
    private val getPrayersUseCase: GetPrayersUseCase
) : ScreenModel {

    private val _prayer = MutableStateFlow<UiState<Prayer>>(UiState.Loading)
    val prayer: StateFlow<UiState<Prayer>> = _prayer.asStateFlow()

    init {
        loadPrayer()
    }

    private fun loadPrayer() {
        _prayer.value = UiState.Loading
        screenModelScope.launch {
            try {
                val p = getPrayersUseCase.byId(prayerId)
                if (p != null) {
                    _prayer.value = UiState.Success(p)
                } else {
                    _prayer.value = UiState.Empty
                }
            } catch (e: Exception) {
                _prayer.value = UiState.Error(e.message ?: "Failed to load prayer")
            }
        }
    }

    fun retry() {
        loadPrayer()
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
