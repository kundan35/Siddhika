package com.siddhika.ui.auth

import androidx.compose.runtime.Composable

@Composable
actual fun GoogleSignInHandler(
    onTokenReceived: (String) -> Unit,
    onError: (String) -> Unit,
    content: @Composable (triggerSignIn: () -> Unit) -> Unit
) {
    val triggerSignIn: () -> Unit = {
        onError("Google Sign-In is not supported on Desktop. Please use email/password.")
    }

    content(triggerSignIn)
}
