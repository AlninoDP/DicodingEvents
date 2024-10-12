package com.example.dicodingevents.ui.finishedevents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class FinishedEventsViewModel : ViewModel() {

    private val _listEventsItem = MutableLiveData<List<ListEventsItem>>()
    val listEventsItem: LiveData<List<ListEventsItem>> = _listEventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "upcomingEventsViewModel"
    }

    init {
        viewModelScope.launch {
            getUpcomingEvents()
        }
    }


    private suspend fun getUpcomingEvents() {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEvents(0)
            val listEvents = response.listEvents
            _listEventsItem.value = listEvents
            _isLoading.value = false

        } catch (e: Exception) {
            _isLoading.value = false
            Log.d(TAG, "${e.message}")
        }

    }
}