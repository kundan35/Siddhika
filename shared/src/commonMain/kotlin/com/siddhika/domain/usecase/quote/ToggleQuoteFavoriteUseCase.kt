package com.siddhika.domain.usecase.quote

import com.siddhika.domain.repository.QuoteRepository

class ToggleQuoteFavoriteUseCase(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}
