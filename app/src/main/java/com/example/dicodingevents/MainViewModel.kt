package com.example.dicodingevents

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel (private val preferences: SettingPreferences): ViewModel() {

    fun getThemeSettings() : LiveData<Boolean>{
        return preferences.getThemeSetting().asLiveData()
    }
}