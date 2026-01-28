package com.siddhika

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.siddhika.core.di.networkModule
import com.siddhika.core.di.platformModule
import com.siddhika.core.di.sharedModule
import com.siddhika.di.appModule
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(platformModule, networkModule, sharedModule, appModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Siddhika",
        state = rememberWindowState(width = 400.dp, height = 800.dp)
    ) {
        App()
    }
}
