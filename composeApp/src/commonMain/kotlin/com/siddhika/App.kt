package com.siddhika

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.siddhika.core.util.AuthState
import com.siddhika.domain.usecase.auth.GetAuthStateUseCase
import com.siddhika.ui.screens.auth.LoginScreen
import com.siddhika.ui.screens.home.HomeScreen
import com.siddhika.ui.theme.SiddhikaTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    SiddhikaTheme {
        val getAuthStateUseCase: GetAuthStateUseCase = koinInject()
        val authState by getAuthStateUseCase().collectAsState(initial = AuthState.Loading)

        when (authState) {
            is AuthState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AuthState.Authenticated -> {
                Navigator(HomeScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
            is AuthState.Unauthenticated, is AuthState.Error -> {
                Navigator(LoginScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}
