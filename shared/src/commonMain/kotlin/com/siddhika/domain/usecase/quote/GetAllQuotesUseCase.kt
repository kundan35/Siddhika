package com.siddhika.domain.usecase.quote

import com.siddhika.domain.model.Quote
import com.siddhika.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllQuotesUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<List<Quote>> = repository.getAllQuotes()

    fun byCategory(category: String): Flow<List<Quote>> = repository.getQuotesByCategory(category)

    fun favorites(): Flow<List<Quote>> = repository.getFavoriteQuotes()
}
