package com.siddhika

import com.siddhika.core.di.platformModule
import com.siddhika.core.di.sharedModule
import com.siddhika.data.SeedData
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.di.appModule
import org.koin.core.context.startKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun initKoin() {
    startKoin {
        modules(platformModule, sharedModule, appModule)
    }

    // Seed the database with initial data
    val helper = DatabaseSeeder()
    helper.seedDatabase()
}

class DatabaseSeeder : KoinComponent {
    private val database: SiddhikaDatabase by inject()

    fun seedDatabase() {
        SeedData.populateDatabase(database)
    }
}
