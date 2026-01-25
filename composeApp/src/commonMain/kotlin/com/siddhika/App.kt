package com.siddhika

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.siddhika.ui.screens.home.HomeScreen
import com.siddhika.ui.theme.SiddhikaTheme

@Composable
fun App() {
    SiddhikaTheme {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
