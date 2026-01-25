package com.siddhika.ui.screens.meditation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.Constants
import com.siddhika.domain.model.Meditation
import com.siddhika.domain.usecase.meditation.GetMeditationsUseCase
import com.siddhika.domain.usecase.meditation.SaveMeditationSessionUseCase
import com.siddhika.ui.components.TimerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MeditationViewModel(
    getMeditationsUseCase: GetMeditationsUseCase
) : ScreenModel {

    val meditations: StateFlow<List<Meditation>> = getMeditationsUseCase()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

class MeditationTimerViewModel(
    private val meditationId: Long,
    private val getMeditationsUseCase: GetMeditationsUseCase,
    private val saveMeditationSessionUseCase: SaveMeditationSessionUseCase
) : ScreenModel {

    private val _meditation = MutableStateFlow<Meditation?>(null)
    val meditation: StateFlow<Meditation?> = _meditation.asStateFlow()

    private val _totalSeconds = MutableStateFlow(0)
    val totalSeconds: StateFlow<Int> = _totalSeconds.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private val _timerState = MutableStateFlow(TimerState.Idle)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private var timerJob: Job? = null
    private var elapsedSeconds = 0

    init {
        loadMeditation()
    }

    private fun loadMeditation() {
        screenModelScope.launch {
            val med = getMeditationsUseCase.byId(meditationId)
            _meditation.value = med
            med?.let {
                val seconds = it.durationMinutes * 60
                _totalSeconds.value = seconds
                _remainingSeconds.value = seconds
            }
        }
    }

    fun startTimer() {
        _timerState.value = TimerState.Running
        timerJob = screenModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(Constants.TIMER_TICK_INTERVAL_MS)
                _remainingSeconds.value -= 1
                elapsedSeconds += 1
            }
            onTimerComplete()
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.value = TimerState.Paused
    }

    fun stopTimer() {
        timerJob?.cancel()
        if (elapsedSeconds > 0) {
            saveSession()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _remainingSeconds.value = _totalSeconds.value
        _timerState.value = TimerState.Idle
        elapsedSeconds = 0
    }

    private fun onTimerComplete() {
        _timerState.value = TimerState.Completed
        saveSession()
    }

    private fun saveSession() {
        if (elapsedSeconds > 0) {
            screenModelScope.launch {
                saveMeditationSessionUseCase(meditationId, elapsedSeconds)
                elapsedSeconds = 0
            }
        }
    }
}
