package com.kriscg.laboratorio8.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val userNameKey = stringPreferencesKey("user_name")

    val userName = dataStore.data.map { preferences ->
        preferences[userNameKey] ?: ""
    }
    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[userNameKey] = name
        }
    }
    suspend fun clearUserName() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}


