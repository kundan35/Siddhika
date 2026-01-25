package com.siddhika.domain.usecase.meditation

import com.siddhika.domain.model.MeditationSession
import com.siddhika.domain.repository.MeditationRepository
import kotlinx.coroutines.flow.Flow

data class MeditationStats(
    val totalSessions: Int,
    val totalMinutes: Int,
    val averageSessionMinutes: Int
)

class GetMeditationStatsUseCase(
    private val repository: MeditationRepository
) {
    fun getSessions(): Flow<List<MeditationSession>> = repository.getAllSessions()

    suspend fun getTotalTimeSeconds(): Long = repository.getTotalMeditationTimeSeconds()
}
