package com.siddhika.domain.usecase.meditation

import com.siddhika.domain.model.Meditation
import com.siddhika.domain.repository.MeditationRepository
import kotlinx.coroutines.flow.Flow

class GetMeditationsUseCase(
    private val repository: MeditationRepository
) {
    operator fun invoke(): Flow<List<Meditation>> = repository.getAllMeditations()

    fun byCategory(category: String): Flow<List<Meditation>> = repository.getMeditationsByCategory(category)

    suspend fun byId(id: Long): Meditation? = repository.getMeditationById(id)
}
