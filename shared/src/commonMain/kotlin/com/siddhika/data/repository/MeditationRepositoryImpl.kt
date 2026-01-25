package com.siddhika.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.data.mapper.toDomain
import com.siddhika.domain.model.Meditation
import com.siddhika.domain.model.MeditationSession
import com.siddhika.domain.repository.MeditationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MeditationRepositoryImpl(
    private val database: SiddhikaDatabase
) : MeditationRepository {

    private val queries = database.siddhikaDatabaseQueries

    override fun getAllMeditations(): Flow<List<Meditation>> =
        queries.getAllMeditations()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getMeditationsByCategory(category: String): Flow<List<Meditation>> =
        queries.getMeditationsByCategory(category)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getMeditationById(id: Long): Meditation? = withContext(Dispatchers.IO) {
        queries.getMeditationById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun addMeditation(meditation: Meditation) = withContext(Dispatchers.IO) {
        queries.insertMeditation(
            title = meditation.title,
            description = meditation.description,
            durationMinutes = meditation.durationMinutes.toLong(),
            category = meditation.category,
            imageUrl = meditation.imageUrl,
            audioUrl = meditation.audioUrl
        )
    }

    override fun getAllSessions(): Flow<List<MeditationSession>> =
        queries.getAllSessions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getSessionsByMeditationId(meditationId: Long): Flow<List<MeditationSession>> =
        queries.getSessionsByMeditationId(meditationId)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveSession(session: MeditationSession) = withContext(Dispatchers.IO) {
        queries.insertSession(
            meditationId = session.meditationId,
            completedAt = session.completedAt.toEpochMilliseconds(),
            durationSeconds = session.durationSeconds.toLong()
        )
    }

    override suspend fun getTotalMeditationTimeSeconds(): Long = withContext(Dispatchers.IO) {
        queries.getTotalMeditationTime().executeAsOneOrNull()?.SUM ?: 0L
    }
}
