package com.siddhika.ui.auth

import androidx.compose.runtime.Composable

@Composable
actual fun GoogleSignInHandler(
    onTokenReceived: (String) -> Unit,
    onError: (String) -> Unit,
    content: @Composable (triggerSignIn: () -> Unit) -> Unit
) {
    val triggerSignIn: () -> Unit = {
        // iOS Google Sign-In requires native integration
        // For now, show not implemented message
        onError("Google Sign-In on iOS requires additional native setup")
    }

    content(triggerSignIn)
}
