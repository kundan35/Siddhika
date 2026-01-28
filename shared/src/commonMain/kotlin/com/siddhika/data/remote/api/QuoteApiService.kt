package com.siddhika.data.remote.api

import com.siddhika.data.remote.dto.QuoteDto
import io.ktor.client.call.*
import io.ktor.client.request.*

class QuoteApiService(private val api: SiddhikaApi) {

    suspend fun getAll(): List<QuoteDto> =
        api.client.get("${api.baseUrl}/api/quotes").body()

    suspend fun getById(id: Long): QuoteDto =
        api.client.get("${api.baseUrl}/api/quotes/$id").body()

    suspend fun getByCategory(category: String): List<QuoteDto> =
        api.client.get("${api.baseUrl}/api/quotes/category/$category").body()
}
