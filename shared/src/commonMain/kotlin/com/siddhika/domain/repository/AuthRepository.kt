package com.siddhika.domain.repository

import com.siddhika.core.util.AuthState
import com.siddhika.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState>
    val currentUser: User?

    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signUpWithEmail(email: String, password: String, displayName: String?): Result<User>
    suspend fun signInWithGoogle(idToken: String): Result<User>
    suspend fun signInWithApple(idToken: String, nonce: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun sendEmailVerification(): Result<Unit>
    suspend fun updateProfile(displayName: String?, photoUrl: String?): Result<User>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun getIdToken(forceRefresh: Boolean = false): Result<String>
}
