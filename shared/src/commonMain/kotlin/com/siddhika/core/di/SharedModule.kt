package com.siddhika.core.di

import com.siddhika.data.repository.AuthRepositoryImpl
import com.siddhika.data.repository.MeditationRepositoryImpl
import com.siddhika.data.repository.PrayerRepositoryImpl
import com.siddhika.data.repository.QuoteRepositoryImpl
import com.siddhika.data.repository.ScriptureRepositoryImpl
import com.siddhika.domain.repository.AuthRepository
import com.siddhika.domain.repository.MeditationRepository
import com.siddhika.domain.repository.PrayerRepository
import com.siddhika.domain.repository.QuoteRepository
import com.siddhika.domain.repository.ScriptureRepository
import com.siddhika.domain.usecase.auth.DeleteAccountUseCase
import com.siddhika.domain.usecase.auth.GetAuthStateUseCase
import com.siddhika.domain.usecase.auth.SendEmailVerificationUseCase
import com.siddhika.domain.usecase.auth.SendPasswordResetUseCase
import com.siddhika.domain.usecase.auth.SignInWithAppleUseCase
import com.siddhika.domain.usecase.auth.SignInWithEmailUseCase
import com.siddhika.domain.usecase.auth.SignInWithGoogleUseCase
import com.siddhika.domain.usecase.auth.SignOutUseCase
import com.siddhika.domain.usecase.auth.SignUpWithEmailUseCase
import com.siddhika.domain.usecase.auth.UpdateProfileUseCase
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
    // Repositories (injected with both database and API service)
    single<QuoteRepository> { QuoteRepositoryImpl(get(), get()) }
    single<MeditationRepository> { MeditationRepositoryImpl(get(), get()) }
    single<PrayerRepository> { PrayerRepositoryImpl(get(), get()) }
    single<ScriptureRepository> { ScriptureRepositoryImpl(get(), get()) }
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class

    // Auth Use Cases
    factoryOf(::GetAuthStateUseCase)
    factoryOf(::SignInWithEmailUseCase)
    factoryOf(::SignUpWithEmailUseCase)
    factoryOf(::SignInWithGoogleUseCase)
    factoryOf(::SignInWithAppleUseCase)
    factoryOf(::SignOutUseCase)
    factoryOf(::SendPasswordResetUseCase)
    factoryOf(::SendEmailVerificationUseCase)
    factoryOf(::UpdateProfileUseCase)
    factoryOf(::DeleteAccountUseCase)

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
