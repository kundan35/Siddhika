package com.siddhika.data.remote.api

import com.siddhika.data.remote.dto.PrayerDto
import io.ktor.client.call.*
import io.ktor.client.request.*

class PrayerApiService(private val api: SiddhikaApi) {

    suspend fun getAll(): List<PrayerDto> =
        api.client.get("${api.baseUrl}/api/prayers").body()

    suspend fun getById(id: Long): PrayerDto =
        api.client.get("${api.baseUrl}/api/prayers/$id").body()

    suspend fun getByCategory(category: String): List<PrayerDto> =
        api.client.get("${api.baseUrl}/api/prayers/category/$category").body()
}
