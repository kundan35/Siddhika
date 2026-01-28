package com.siddhika

import com.siddhika.core.di.networkModule
import com.siddhika.core.di.platformModule
import com.siddhika.core.di.sharedModule
import com.siddhika.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(platformModule, networkModule, sharedModule, appModule)
    }
}
