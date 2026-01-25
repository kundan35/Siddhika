package com.siddhika.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.siddhika.core.util.DateTimeUtil
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.data.mapper.toDomain
import com.siddhika.domain.model.Quote
import com.siddhika.domain.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class QuoteRepositoryImpl(
    private val database: SiddhikaDatabase
) : QuoteRepository {

    private val queries = database.siddhikaDatabaseQueries

    override fun getAllQuotes(): Flow<List<Quote>> =
        queries.getAllQuotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getDailyQuote(): Flow<Quote?> =
        queries.getDailyQuote()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDomain() }

    override fun getFavoriteQuotes(): Flow<List<Quote>> =
        queries.getFavoriteQuotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getQuotesByCategory(category: String): Flow<List<Quote>> =
        queries.getQuotesByCategory(category)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getQuoteById(id: Long): Quote? = withContext(Dispatchers.IO) {
        queries.getQuoteById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun addQuote(quote: Quote) = withContext(Dispatchers.IO) {
        queries.insertQuote(
            text = quote.text,
            author = quote.author,
            source = quote.source,
            category = quote.category,
            isFavorite = if (quote.isFavorite) 1L else 0L,
            createdAt = DateTimeUtil.nowMillis()
        )
    }

    override suspend fun toggleFavorite(id: Long, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        queries.updateQuoteFavorite(if (isFavorite) 1L else 0L, id)
    }

    override suspend fun deleteQuote(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteQuote(id)
    }
}
