package com.siddhika

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.siddhika.core.di.platformModule
import com.siddhika.core.di.sharedModule
import com.siddhika.data.SeedData
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.di.appModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() = application {
    startKoin {
        modules(platformModule, sharedModule, appModule)
    }

    // Seed the database
    val database: SiddhikaDatabase = getKoin().get()
    SeedData.populateDatabase(database)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Siddhika",
        state = rememberWindowState(width = 400.dp, height = 800.dp)
    ) {
        App()
    }
}
