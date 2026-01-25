package com.siddhika.core.di

import com.siddhika.data.repository.MeditationRepositoryImpl
import com.siddhika.data.repository.PrayerRepositoryImpl
import com.siddhika.data.repository.QuoteRepositoryImpl
import com.siddhika.data.repository.ScriptureRepositoryImpl
import com.siddhika.domain.repository.MeditationRepository
import com.siddhika.domain.repository.PrayerRepository
import com.siddhika.domain.repository.QuoteRepository
import com.siddhika.domain.repository.ScriptureRepository
import com.siddhika.domain.usecase.meditation.GetMeditationStatsUseCase
import com.siddhika.domain.usecase.meditation.GetMeditationsUseCase
import com.siddhika.domain.usecase.meditation.SaveMeditationSessionUseCase
import com.siddhika.domain.usecase.prayer.GetPrayerRemindersUseCase
import com.siddhika.domain.usecase.prayer.GetPrayersUseCase
import com.siddhika.domain.usecase.prayer.SetPrayerReminderUseCase
import com.siddhika.domain.usecase.quote.GetAllQuotesUseCase
import com.siddhika.domain.usecase.quote.GetDailyQuoteUseCase
import com.siddhika.domain.usecase.quote.ToggleQuoteFavoriteUseCase
import com.siddhika.domain.usecase.scripture.GetScriptureContentUseCase
import com.siddhika.domain.usecase.scripture.GetScripturesUseCase
import com.siddhika.domain.usecase.scripture.ToggleBookmarkUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    // Repositories
    singleOf(::QuoteRepositoryImpl) bind QuoteRepository::class
    singleOf(::MeditationRepositoryImpl) bind MeditationRepository::class
    singleOf(::PrayerRepositoryImpl) bind PrayerRepository::class
    singleOf(::ScriptureRepositoryImpl) bind ScriptureRepository::class

    // Quote Use Cases
    factoryOf(::GetDailyQuoteUseCase)
    factoryOf(::GetAllQuotesUseCase)
    factoryOf(::ToggleQuoteFavoriteUseCase)

    // Meditation Use Cases
    factoryOf(::GetMeditationsUseCase)
    factoryOf(::SaveMeditationSessionUseCase)
    factoryOf(::GetMeditationStatsUseCase)

    // Prayer Use Cases
    factoryOf(::GetPrayersUseCase)
    factoryOf(::SetPrayerReminderUseCase)
    factoryOf(::GetPrayerRemindersUseCase)

    // Scripture Use Cases
    factoryOf(::GetScripturesUseCase)
    factoryOf(::GetScriptureContentUseCase)
    factoryOf(::ToggleBookmarkUseCase)
}
