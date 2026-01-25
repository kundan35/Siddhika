package com.siddhika.core.di

import com.siddhika.data.DatabaseDriverFactory
import com.siddhika.data.local.database.SiddhikaDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single { SiddhikaDatabase(get<DatabaseDriverFactory>().create()) }
}
