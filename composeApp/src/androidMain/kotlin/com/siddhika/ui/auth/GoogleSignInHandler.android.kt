package com.siddhika.ui.auth

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

private const val WEB_CLIENT_ID = "454750104809-7j218bjmmbao4vi1mhanbc0bmstppej1.apps.googleusercontent.com"

@Composable
actual fun GoogleSignInHandler(
    onTokenReceived: (String) -> Unit,
    onError: (String) -> Unit,
    content: @Composable (triggerSignIn: () -> Unit) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    val triggerSignIn: () -> Unit = {
        if (activity == null) {
            onError("Activity context required for Google Sign-In")
        } else {
            scope.launch {
                try {
                    val credentialManager = CredentialManager.create(context)

                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(WEB_CLIENT_ID)
                        .setAutoSelectEnabled(false)
                        .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    val result = credentialManager.getCredential(
                        request = request,
                        context = activity
                    )

                    val credential = result.credential
                    if (credential is CustomCredential &&
                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        onTokenReceived(googleIdTokenCredential.idToken)
                    } else {
                        onError("Unexpected credential type")
                    }
                } catch (e: GetCredentialCancellationException) {
                    onError("Sign-in cancelled")
                } catch (e: Exception) {
                    onError(e.message ?: "Google Sign-In failed")
                }
            }
        }
    }

    content(triggerSignIn)
}
