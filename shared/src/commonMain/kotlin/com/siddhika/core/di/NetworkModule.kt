package com.siddhika.core.di

import com.siddhika.data.remote.api.HttpClientFactory
import com.siddhika.data.remote.api.MeditationApiService
import com.siddhika.data.remote.api.PrayerApiService
import com.siddhika.data.remote.api.QuoteApiService
import com.siddhika.data.remote.api.ScriptureApiService
import com.siddhika.data.remote.api.SiddhikaApi
import org.koin.dsl.module

expect fun getBaseUrl(): String

val networkModule = module {
    single { HttpClientFactory.create() }
    single { SiddhikaApi(baseUrl = getBaseUrl(), client = get()) }
    single { QuoteApiService(get()) }
    single { MeditationApiService(get()) }
    single { PrayerApiService(get()) }
    single { ScriptureApiService(get()) }
}
