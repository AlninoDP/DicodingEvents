package com.example.dicodingevents.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.data.remote.retrofit.ApiConfig
import com.example.dicodingevents.utils.Event
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _listFinishedEventsItem = MutableLiveData<List<ListEventsItem>>()
    val listFinishedEventsItem: LiveData<List<ListEventsItem>> = _listFinishedEventsItem

    private val _listUpcomingEventsItem = MutableLiveData<List<ListEventsItem>>()
    val listUpcomingEventsItem: LiveData<List<ListEventsItem>> = _listUpcomingEventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    companion object {
        private const val TAG = "homeViewModel"
    }

    init {
        viewModelScope.launch {
            getFinishedEvents()
            getUpcomingEvents()
        }
    }


    private suspend fun getFinishedEvents() {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEvents(0, limit = 5)
            val listEvents = response.listEvents
            _listFinishedEventsItem.value = listEvents
            _isLoading.value = false

        } catch (e: Exception) {
            _isLoading.value = false
            _snackBarText.value = Event("Failed to Load Data, Error: ${e.message}")
            Log.d(TAG, "${e.message}")
        }

    }

    private suspend fun getUpcomingEvents() {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEvents(1, limit = 5)
            val listEvents = response.listEvents
            _listUpcomingEventsItem.value = listEvents
            _isLoading.value = false

        } catch (e: Exception) {
            _isLoading.value = false
            _snackBarText.value = Event("Failed to Load Data, Error: ${e.message}")
            Log.d(TAG, "${e.message}")
        }

    }
}