package com.siddhika.domain.repository

import com.siddhika.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getAllQuotes(): Flow<List<Quote>>
    fun getDailyQuote(): Flow<Quote?>
    fun getFavoriteQuotes(): Flow<List<Quote>>
    fun getQuotesByCategory(category: String): Flow<List<Quote>>
    suspend fun getQuoteById(id: Long): Quote?
    suspend fun addQuote(quote: Quote)
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)
    suspend fun deleteQuote(id: Long)
}
