package com.siddhika.domain.usecase.scripture

import com.siddhika.domain.model.Scripture
import com.siddhika.domain.repository.ScriptureRepository

class GetScriptureContentUseCase(
    private val repository: ScriptureRepository
) {
    suspend operator fun invoke(id: Long): Scripture? = repository.getScriptureById(id)
}
