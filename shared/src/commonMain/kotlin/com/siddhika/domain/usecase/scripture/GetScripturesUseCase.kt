package com.siddhika.domain.usecase.scripture

import com.siddhika.domain.model.Scripture
import com.siddhika.domain.repository.ScriptureRepository
import kotlinx.coroutines.flow.Flow

class GetScripturesUseCase(
    private val repository: ScriptureRepository
) {
    operator fun invoke(): Flow<List<Scripture>> = repository.getAllScriptures()

    fun byCategory(category: String): Flow<List<Scripture>> = repository.getScripturesByCategory(category)

    suspend fun byId(id: Long): Scripture? = repository.getScriptureById(id)
}
