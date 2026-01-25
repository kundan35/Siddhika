package com.siddhika.di

import com.siddhika.ui.screens.home.HomeViewModel
import com.siddhika.ui.screens.meditation.MeditationTimerViewModel
import com.siddhika.ui.screens.meditation.MeditationViewModel
import com.siddhika.ui.screens.prayers.AddReminderViewModel
import com.siddhika.ui.screens.prayers.PrayerDetailViewModel
import com.siddhika.ui.screens.prayers.PrayerViewModel
import com.siddhika.ui.screens.quotes.QuotesViewModel
import com.siddhika.ui.screens.scripture.BookmarksViewModel
import com.siddhika.ui.screens.scripture.ScriptureReaderViewModel
import com.siddhika.ui.screens.scripture.ScriptureViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    factoryOf(::HomeViewModel)

    // Meditation
    factoryOf(::MeditationViewModel)
    factory { params -> MeditationTimerViewModel(params.get(), get(), get()) }

    // Quotes
    factoryOf(::QuotesViewModel)

    // Prayers
    factoryOf(::PrayerViewModel)
    factory { params -> PrayerDetailViewModel(params.get(), get()) }
    factoryOf(::AddReminderViewModel)

    // Scripture
    factoryOf(::ScriptureViewModel)
    factory { params -> ScriptureReaderViewModel(params.get(), get(), get()) }
    factoryOf(::BookmarksViewModel)
}
