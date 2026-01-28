package com.siddhika.data.remote.api

import com.siddhika.data.remote.dto.MeditationDto
import io.ktor.client.call.*
import io.ktor.client.request.*

class MeditationApiService(private val api: SiddhikaApi) {

    suspend fun getAll(): List<MeditationDto> =
        api.client.get("${api.baseUrl}/api/meditations").body()

    suspend fun getById(id: Long): MeditationDto =
        api.client.get("${api.baseUrl}/api/meditations/$id").body()

    suspend fun getByCategory(category: String): List<MeditationDto> =
        api.client.get("${api.baseUrl}/api/meditations/category/$category").body()
}
