package com.siddhika.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_tokens")

class AuthTokenStorageImpl(private val context: Context) : AuthTokenStorage {

    private object Keys {
        val ID_TOKEN = stringPreferencesKey("id_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveToken(token: String) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.ID_TOKEN] = token
        }
    }

    override suspend fun getToken(): String? {
        return context.authDataStore.data.map { prefs ->
            prefs[Keys.ID_TOKEN]
        }.first()
    }

    override suspend fun clearToken() {
        context.authDataStore.edit { prefs ->
            prefs.remove(Keys.ID_TOKEN)
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.REFRESH_TOKEN] = token
        }
    }

    override suspend fun getRefreshToken(): String? {
        return context.authDataStore.data.map { prefs ->
            prefs[Keys.REFRESH_TOKEN]
        }.first()
    }

    override suspend fun clearAll() {
        context.authDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
