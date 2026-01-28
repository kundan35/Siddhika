package com.siddhika.data.auth

import com.siddhika.data.auth.model.FirebaseAuthUser
import kotlinx.coroutines.flow.Flow

expect class FirebaseAuthService() {
    val currentUser: FirebaseAuthUser?
    val authStateFlow: Flow<FirebaseAuthUser?>

    suspend fun signInWithEmail(email: String, password: String): FirebaseAuthUser
    suspend fun signUpWithEmail(email: String, password: String): FirebaseAuthUser
    suspend fun signInWithCredential(idToken: String, provider: String, nonce: String? = null): FirebaseAuthUser
    suspend fun signOut()
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun sendEmailVerification()
    suspend fun updateProfile(displayName: String?, photoUrl: String?)
    suspend fun deleteAccount()
    suspend fun getIdToken(forceRefresh: Boolean): String?
    suspend fun reloadUser()
}
