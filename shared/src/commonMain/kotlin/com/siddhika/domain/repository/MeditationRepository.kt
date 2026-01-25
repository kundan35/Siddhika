package com.siddhika.domain.repository

import com.siddhika.domain.model.Meditation
import com.siddhika.domain.model.MeditationSession
import kotlinx.coroutines.flow.Flow

interface MeditationRepository {
    fun getAllMeditations(): Flow<List<Meditation>>
    fun getMeditationsByCategory(category: String): Flow<List<Meditation>>
    suspend fun getMeditationById(id: Long): Meditation?
    suspend fun addMeditation(meditation: Meditation)

    fun getAllSessions(): Flow<List<MeditationSession>>
    fun getSessionsByMeditationId(meditationId: Long): Flow<List<MeditationSession>>
    suspend fun saveSession(session: MeditationSession)
    suspend fun getTotalMeditationTimeSeconds(): Long
}
