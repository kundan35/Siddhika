package com.siddhika.domain.usecase.meditation

import com.siddhika.core.util.DateTimeUtil
import com.siddhika.domain.model.MeditationSession
import com.siddhika.domain.repository.MeditationRepository

class SaveMeditationSessionUseCase(
    private val repository: MeditationRepository
) {
    suspend operator fun invoke(meditationId: Long, durationSeconds: Int) {
        val session = MeditationSession(
            meditationId = meditationId,
            completedAt = DateTimeUtil.now(),
            durationSeconds = durationSeconds
        )
        repository.saveSession(session)
    }
}
