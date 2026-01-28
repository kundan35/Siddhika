package com.siddhika.data.auth

import platform.Foundation.NSUserDefaults

/**
 * iOS implementation of AuthTokenStorage using NSUserDefaults.
 * Note: For production, consider using Keychain for more secure token storage.
 */
class AuthTokenStorageImpl : AuthTokenStorage {

    private val defaults = NSUserDefaults.standardUserDefaults

    override suspend fun saveToken(token: String) {
        defaults.setObject(token, KEY_ID_TOKEN)
        defaults.synchronize()
    }

    override suspend fun getToken(): String? {
        return defaults.stringForKey(KEY_ID_TOKEN)
    }

    override suspend fun clearToken() {
        defaults.removeObjectForKey(KEY_ID_TOKEN)
        defaults.synchronize()
    }

    override suspend fun saveRefreshToken(token: String) {
        defaults.setObject(token, KEY_REFRESH_TOKEN)
        defaults.synchronize()
    }

    override suspend fun getRefreshToken(): String? {
        return defaults.stringForKey(KEY_REFRESH_TOKEN)
    }

    override suspend fun clearAll() {
        defaults.removeObjectForKey(KEY_ID_TOKEN)
        defaults.removeObjectForKey(KEY_REFRESH_TOKEN)
        defaults.synchronize()
    }

    companion object {
        private const val KEY_ID_TOKEN = "com.siddhika.auth.id_token"
        private const val KEY_REFRESH_TOKEN = "com.siddhika.auth.refresh_token"
    }
}
