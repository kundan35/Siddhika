package com.siddhika.core.di

import com.siddhika.data.DatabaseDriverFactory
import com.siddhika.data.auth.AuthTokenStorage
import com.siddhika.data.auth.AuthTokenStorageImpl
import com.siddhika.data.auth.FirebaseAuthService
import com.siddhika.data.auth.GoogleAuthProvider
import com.siddhika.data.local.database.SiddhikaDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single { SiddhikaDatabase(get<DatabaseDriverFactory>().create()) }

    // Auth
    single { FirebaseAuthService() }
    single<AuthTokenStorage> { AuthTokenStorageImpl(androidContext()) }
    single {
        GoogleAuthProvider(
            context = androidContext(),
            webClientId = "454750104809-7j218bjmmbao4vi1mhanbc0bmstppej1.apps.googleusercontent.com"
        )
    }
}
