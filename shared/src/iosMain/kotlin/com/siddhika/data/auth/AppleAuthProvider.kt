package com.siddhika.data.auth

import platform.Foundation.NSUUID

/**
 * Apple Sign-In provider for iOS.
 * Note: The actual Apple Sign-In must be triggered from Swift/UIKit
 * using ASAuthorizationController.
 * This class provides helper methods and a bridge to receive credentials from Swift.
 */
class AppleAuthProvider {
    private var pendingIdToken: String? = null
    private var pendingNonce: String? = null
    private var pendingError: Throwable? = null

    /**
     * Generates a random nonce for Apple Sign-In.
     * The raw nonce needs to be SHA256 hashed before sending to Apple.
     */
    fun generateNonce(): String {
        return NSUUID().UUIDString
    }

    /**
     * Called from Swift when Apple Sign-In completes successfully.
     */
    fun onAppleSignInSuccess(idToken: String, nonce: String) {
        pendingIdToken = idToken
        pendingNonce = nonce
        pendingError = null
    }

    /**
     * Called from Swift when Apple Sign-In fails.
     */
    fun onAppleSignInError(error: String) {
        pendingIdToken = null
        pendingNonce = null
        pendingError = IllegalStateException(error)
    }

    /**
     * Get the pending credentials.
     * Returns null if no credentials are available.
     */
    fun getPendingCredentials(): Pair<String, String>? {
        val token = pendingIdToken
        val nonce = pendingNonce
        return if (token != null && nonce != null) {
            Pair(token, nonce)
        } else {
            null
        }
    }

    /**
     * Clear the pending state.
     */
    fun clearPendingState() {
        pendingIdToken = null
        pendingNonce = null
        pendingError = null
    }
}
