package com.siddhika.data.auth

import com.siddhika.data.auth.model.FirebaseAuthUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.OAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

actual class FirebaseAuthService {
    private val auth: FirebaseAuth = Firebase.auth

    actual val currentUser: FirebaseAuthUser?
        get() = auth.currentUser?.toFirebaseAuthUser()

    actual val authStateFlow: Flow<FirebaseAuthUser?>
        get() = auth.authStateChanged.map { it?.toFirebaseAuthUser() }

    actual suspend fun signInWithEmail(email: String, password: String): FirebaseAuthUser {
        val result = auth.signInWithEmailAndPassword(email, password)
        return result.user?.toFirebaseAuthUser()
            ?: throw IllegalStateException("Sign in failed: no user returned")
    }

    actual suspend fun signUpWithEmail(email: String, password: String): FirebaseAuthUser {
        val result = auth.createUserWithEmailAndPassword(email, password)
        return result.user?.toFirebaseAuthUser()
            ?: throw IllegalStateException("Sign up failed: no user returned")
    }

    actual suspend fun signInWithCredential(
        idToken: String,
        provider: String,
        nonce: String?
    ): FirebaseAuthUser {
        val credential = when (provider) {
            "google.com" -> GoogleAuthProvider.credential(idToken, null)
            "apple.com" -> OAuthProvider.credential("apple.com", idToken, nonce)
            else -> throw IllegalArgumentException("Unsupported provider: $provider")
        }
        val result = auth.signInWithCredential(credential)
        return result.user?.toFirebaseAuthUser()
            ?: throw IllegalStateException("Sign in with credential failed: no user returned")
    }

    actual suspend fun signOut() {
        auth.signOut()
    }

    actual suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    actual suspend fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
            ?: throw IllegalStateException("No user signed in")
    }

    actual suspend fun updateProfile(displayName: String?, photoUrl: String?) {
        val user = auth.currentUser ?: throw IllegalStateException("No user signed in")
        user.updateProfile(displayName, photoUrl)
    }

    actual suspend fun deleteAccount() {
        auth.currentUser?.delete()
            ?: throw IllegalStateException("No user signed in")
    }

    actual suspend fun getIdToken(forceRefresh: Boolean): String? {
        return auth.currentUser?.getIdToken(forceRefresh)
    }

    actual suspend fun reloadUser() {
        auth.currentUser?.reload()
    }

    private fun dev.gitlive.firebase.auth.FirebaseUser.toFirebaseAuthUser(): FirebaseAuthUser {
        val providerData = this.providerData.firstOrNull { it.providerId != "firebase" }
        return FirebaseAuthUser(
            uid = uid,
            email = email,
            displayName = displayName,
            photoUrl = photoURL,
            isEmailVerified = isEmailVerified,
            providerId = providerData?.providerId ?: "password"
        )
    }
}
