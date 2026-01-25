package com.siddhika.domain.usecase.quote

import com.siddhika.domain.model.Quote
import com.siddhika.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetDailyQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<Quote?> = repository.getDailyQuote()
}
