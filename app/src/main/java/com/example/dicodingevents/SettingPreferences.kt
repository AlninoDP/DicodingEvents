package com.example.dicodingevents

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val EVENT_NOTIFICATION_KEY = booleanPreferencesKey("notification_settings")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false

        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getEventNotificationSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[EVENT_NOTIFICATION_KEY] ?: false
        }
    }

    suspend fun saveEventNotificationSetting(isEventNotificationActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[EVENT_NOTIFICATION_KEY] = isEventNotificationActive
        }
    }


}