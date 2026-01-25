package com.siddhika.domain.usecase.prayer

import com.siddhika.domain.model.Prayer
import com.siddhika.domain.repository.PrayerRepository
import kotlinx.coroutines.flow.Flow

class GetPrayersUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): Flow<List<Prayer>> = repository.getAllPrayers()

    fun byCategory(category: String): Flow<List<Prayer>> = repository.getPrayersByCategory(category)

    suspend fun byId(id: Long): Prayer? = repository.getPrayerById(id)
}
