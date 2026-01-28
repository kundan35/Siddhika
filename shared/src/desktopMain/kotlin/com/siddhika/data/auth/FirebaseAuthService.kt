package com.siddhika.data.auth

import com.siddhika.data.auth.model.FirebaseAuthUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Desktop implementation of FirebaseAuthService using Firebase REST API.
 * Note: Social sign-in (Google, Apple) is not supported on Desktop.
 * Only email/password authentication is available.
 */
actual class FirebaseAuthService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private val apiKey = "AIzaSyB2RHurV16r7C7ivF0vwWXr_yMh96rc0dI"
    private val baseUrl = "https://identitytoolkit.googleapis.com/v1"

    private val _currentUser = MutableStateFlow<FirebaseAuthUser?>(null)
    private val _authState = MutableStateFlow<FirebaseAuthUser?>(null)

    actual val currentUser: FirebaseAuthUser?
        get() = _currentUser.value

    actual val authStateFlow: Flow<FirebaseAuthUser?>
        get() = _authState

    actual suspend fun signInWithEmail(email: String, password: String): FirebaseAuthUser {
        val response = client.post("$baseUrl/accounts:signInWithPassword?key=$apiKey") {
            contentType(ContentType.Application.Json)
            setBody(SignInRequest(email, password, true))
        }.body<SignInResponse>()

        val user = FirebaseAuthUser(
            uid = response.localId,
            email = response.email,
            displayName = response.displayName,
            photoUrl = null,
            isEmailVerified = response.emailVerified ?: false,
            providerId = "password"
        )
        _currentUser.value = user
        _authState.value = user
        return user
    }

    actual suspend fun signUpWithEmail(email: String, password: String): FirebaseAuthUser {
        val response = client.post("$baseUrl/accounts:signUp?key=$apiKey") {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email, password, true))
        }.body<SignUpResponse>()

        val user = FirebaseAuthUser(
            uid = response.localId,
            email = response.email,
            displayName = null,
            photoUrl = null,
            isEmailVerified = false,
            providerId = "password"
        )
        _currentUser.value = user
        _authState.value = user
        return user
    }

    actual suspend fun signInWithCredential(
        idToken: String,
        provider: String,
        nonce: String?
    ): FirebaseAuthUser {
        throw UnsupportedOperationException("Social sign-in is not supported on Desktop. Please use email/password authentication.")
    }

    actual suspend fun signOut() {
        _currentUser.value = null
        _authState.value = null
    }

    actual suspend fun sendPasswordResetEmail(email: String) {
        client.post("$baseUrl/accounts:sendOobCode?key=$apiKey") {
            contentType(ContentType.Application.Json)
            setBody(PasswordResetRequest("PASSWORD_RESET", email))
        }
    }

    actual suspend fun sendEmailVerification() {
        val user = _currentUser.value ?: throw IllegalStateException("No user signed in")
        // Note: This requires the ID token which we'd need to store during sign in
        throw UnsupportedOperationException("Email verification requires ID token management")
    }

    actual suspend fun updateProfile(displayName: String?, photoUrl: String?) {
        // Note: This requires the ID token from sign in
        val currentUser = _currentUser.value ?: throw IllegalStateException("No user signed in")
        _currentUser.value = currentUser.copy(
            displayName = displayName ?: currentUser.displayName,
            photoUrl = photoUrl ?: currentUser.photoUrl
        )
        _authState.value = _currentUser.value
    }

    actual suspend fun deleteAccount() {
        // Note: This requires the ID token from sign in
        _currentUser.value = null
        _authState.value = null
    }

    actual suspend fun getIdToken(forceRefresh: Boolean): String? {
        // Note: Would need to implement token refresh using stored refresh token
        return null
    }

    actual suspend fun reloadUser() {
        // Note: Would need to call getAccountInfo endpoint with ID token
    }

    @Serializable
    private data class SignInRequest(
        val email: String,
        val password: String,
        val returnSecureToken: Boolean
    )

    @Serializable
    private data class SignInResponse(
        val localId: String,
        val email: String,
        val displayName: String? = null,
        val idToken: String,
        val refreshToken: String,
        val expiresIn: String,
        val emailVerified: Boolean? = null
    )

    @Serializable
    private data class SignUpRequest(
        val email: String,
        val password: String,
        val returnSecureToken: Boolean
    )

    @Serializable
    private data class SignUpResponse(
        val localId: String,
        val email: String,
        val idToken: String,
        val refreshToken: String,
        val expiresIn: String
    )

    @Serializable
    private data class PasswordResetRequest(
        val requestType: String,
        val email: String
    )
}
