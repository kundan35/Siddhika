package com.siddhika.ui.auth

import androidx.compose.runtime.Composable

@Composable
expect fun GoogleSignInHandler(
    onTokenReceived: (String) -> Unit,
    onError: (String) -> Unit,
    content: @Composable (triggerSignIn: () -> Unit) -> Unit
)
