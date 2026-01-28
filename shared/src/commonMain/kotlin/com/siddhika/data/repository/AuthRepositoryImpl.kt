package com.siddhika.data.repository

import com.siddhika.core.util.AuthState
import com.siddhika.data.auth.FirebaseAuthService
import com.siddhika.data.mapper.toDomain
import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val firebaseAuthService: FirebaseAuthService
) : AuthRepository {

    override val authState: Flow<AuthState> = firebaseAuthService.authStateFlow
        .map { firebaseUser ->
            if (firebaseUser != null) {
                AuthState.Authenticated(firebaseUser.toDomain())
            } else {
                AuthState.Unauthenticated
            }
        }
        .catch { e ->
            emit(AuthState.Error(e.message ?: "Unknown authentication error"))
        }

    override val currentUser: User?
        get() = firebaseAuthService.currentUser?.toDomain()

    override suspend fun signInWithEmail(email: String, password: String): Result<User> =
        runCatching {
            firebaseAuthService.signInWithEmail(email, password).toDomain()
        }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String?
    ): Result<User> = runCatching {
        val user = firebaseAuthService.signUpWithEmail(email, password)
        if (displayName != null) {
            firebaseAuthService.updateProfile(displayName, null)
            firebaseAuthService.reloadUser()
        }
        firebaseAuthService.currentUser?.toDomain() ?: user.toDomain()
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> = runCatching {
        firebaseAuthService.signInWithCredential(idToken, "google.com").toDomain()
    }

    override suspend fun signInWithApple(idToken: String, nonce: String): Result<User> = runCatching {
        firebaseAuthService.signInWithCredential(idToken, "apple.com", nonce).toDomain()
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        firebaseAuthService.signOut()
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> = runCatching {
        firebaseAuthService.sendPasswordResetEmail(email)
    }

    override suspend fun sendEmailVerification(): Result<Unit> = runCatching {
        firebaseAuthService.sendEmailVerification()
    }

    override suspend fun updateProfile(displayName: String?, photoUrl: String?): Result<User> =
        runCatching {
            firebaseAuthService.updateProfile(displayName, photoUrl)
            firebaseAuthService.reloadUser()
            firebaseAuthService.currentUser?.toDomain()
                ?: throw IllegalStateException("User not found after profile update")
        }

    override suspend fun deleteAccount(): Result<Unit> = runCatching {
        firebaseAuthService.deleteAccount()
    }

    override suspend fun getIdToken(forceRefresh: Boolean): Result<String> = runCatching {
        firebaseAuthService.getIdToken(forceRefresh)
            ?: throw IllegalStateException("No user signed in")
    }
}
