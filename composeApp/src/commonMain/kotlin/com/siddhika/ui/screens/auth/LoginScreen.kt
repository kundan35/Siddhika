package com.siddhika.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.siddhika.ui.auth.GoogleSignInHandler
import com.siddhika.ui.components.AuthTextField
import com.siddhika.ui.components.PrimaryButton
import com.siddhika.ui.components.SocialSignInButton

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<LoginViewModel>()

        val email by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val isGoogleLoading by viewModel.isGoogleLoading.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(errorMessage) {
            errorMessage?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loginSuccess.collect {
                // Navigation will be handled by auth state observer in App.kt
            }
        }

        GoogleSignInHandler(
            onTokenReceived = { idToken ->
                viewModel.signInWithGoogle(idToken)
            },
            onError = { error ->
                viewModel.onGoogleSignInCancelled()
                // Error will be shown via snackbar
            }
        ) { triggerGoogleSignIn ->

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue your spiritual journey",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    AuthTextField(
                        value = email,
                        onValueChange = viewModel::updateEmail,
                        label = "Email",
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = password,
                        onValueChange = viewModel::updatePassword,
                        label = "Password",
                        isPassword = true,
                        imeAction = ImeAction.Done,
                        onImeAction = { viewModel.signInWithEmail() }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = { navigator.push(ForgotPasswordScreen()) },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text("Forgot Password?")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = "Sign In",
                        onClick = { viewModel.signInWithEmail() },
                        isLoading = isLoading,
                        enabled = !isGoogleLoading
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f))
                        Text(
                            text = "  or continue with  ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SocialSignInButton(
                        text = "Continue with Google",
                        onClick = {
                            viewModel.onGoogleSignInRequested {
                                triggerGoogleSignIn()
                            }
                        },
                        isLoading = isGoogleLoading,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(onClick = { navigator.push(RegisterScreen()) }) {
                            Text("Sign Up")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
        } // Close GoogleSignInHandler
    }
}
