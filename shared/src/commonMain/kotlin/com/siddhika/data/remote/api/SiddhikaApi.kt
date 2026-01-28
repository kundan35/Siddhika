package com.siddhika.data.remote.api

import io.ktor.client.*

class SiddhikaApi(
    val baseUrl: String,
    val client: HttpClient
)
