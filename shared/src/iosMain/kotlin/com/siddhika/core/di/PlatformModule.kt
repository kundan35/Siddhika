package com.siddhika.core.di

import com.siddhika.data.DatabaseDriverFactory
import com.siddhika.data.auth.AppleAuthProvider
import com.siddhika.data.auth.AuthTokenStorage
import com.siddhika.data.auth.AuthTokenStorageImpl
import com.siddhika.data.auth.FirebaseAuthService
import com.siddhika.data.auth.GoogleAuthProvider
import com.siddhika.data.local.database.SiddhikaDatabase
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory() }
    single { SiddhikaDatabase(get<DatabaseDriverFactory>().create()) }

    // Auth
    single { FirebaseAuthService() }
    single<AuthTokenStorage> { AuthTokenStorageImpl() }
    single { GoogleAuthProvider() }
    single { AppleAuthProvider() }
}
