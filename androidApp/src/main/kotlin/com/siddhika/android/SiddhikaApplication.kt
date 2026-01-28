package com.siddhika.android

import android.app.Application
import com.siddhika.core.di.networkModule
import com.siddhika.core.di.platformModule
import com.siddhika.core.di.sharedModule
import com.siddhika.data.SeedData
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.di.appModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SiddhikaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SiddhikaApplication)
            modules(platformModule, networkModule, sharedModule, appModule)
        }

        // Seed the database with initial data
        //val database: SiddhikaDatabase by inject()
        //SeedData.populateDatabase(database)
    }
}
