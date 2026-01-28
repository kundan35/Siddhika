package com.siddhika.data.remote.api

import com.siddhika.data.remote.dto.ScriptureDto
import io.ktor.client.call.*
import io.ktor.client.request.*

class ScriptureApiService(private val api: SiddhikaApi) {

    suspend fun getAll(): List<ScriptureDto> =
        api.client.get("${api.baseUrl}/api/scriptures").body()

    suspend fun getById(id: Long): ScriptureDto =
        api.client.get("${api.baseUrl}/api/scriptures/$id").body()

    suspend fun getByCategory(category: String): List<ScriptureDto> =
        api.client.get("${api.baseUrl}/api/scriptures/category/$category").body()
}
