package com.example.dicodingevents

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MainViewModel (private val preferences: SettingPreferences): ViewModel() {

    fun getThemeSettings() : LiveData<Boolean>{
        return preferences.getThemeSetting().asLiveData()
    }

    fun getEventNotificationSetting() : LiveData<Boolean> {
        return preferences.getEventNotificationSetting().asLiveData()
    }

}