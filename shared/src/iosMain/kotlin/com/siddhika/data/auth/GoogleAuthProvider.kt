package com.siddhika.data.auth

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Google Sign-In provider for iOS.
 * Note: The actual Google Sign-In must be triggered from Swift/UIKit
 * as it requires presenting a view controller.
 * This class provides a bridge to receive the ID token from Swift.
 */
class GoogleAuthProvider {
    private var pendingIdToken: String? = null
    private var pendingError: Throwable? = null

    /**
     * Called from Swift when Google Sign-In completes successfully.
     */
    fun onGoogleSignInSuccess(idToken: String) {
        pendingIdToken = idToken
        pendingError = null
    }

    /**
     * Called from Swift when Google Sign-In fails.
     */
    fun onGoogleSignInError(error: String) {
        pendingIdToken = null
        pendingError = IllegalStateException(error)
    }

    /**
     * Get the pending ID token.
     * Returns null if no token is available.
     */
    fun getPendingIdToken(): String? = pendingIdToken

    /**
     * Clear the pending state.
     */
    fun clearPendingState() {
        pendingIdToken = null
        pendingError = null
    }
}
