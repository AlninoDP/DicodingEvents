package com.example.dicodingevents.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel (private val preferences: SettingPreferences): ViewModel() {

    fun getThemeSettings() : LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getEventNotificationSetting() : LiveData<Boolean> {
        return preferences.getEventNotificationSetting().asLiveData()
    }

    fun saveEventNotificationSetting(isEventNotificationActive: Boolean) {
        viewModelScope.launch {
            preferences.saveEventNotificationSetting(isEventNotificationActive)
        }
    }
}