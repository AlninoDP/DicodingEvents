package com.example.dicodingevents.ui.finishedevents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.data.retrofit.ApiConfig
import com.example.dicodingevents.utils.Event
import kotlinx.coroutines.launch

class FinishedEventsViewModel : ViewModel() {

    private val _listEventsItem = MutableLiveData<List<ListEventsItem>>()
    val listEventsItem: LiveData<List<ListEventsItem>> = _listEventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    companion object {
        private const val TAG = "upcomingEventsViewModel"
    }

    init {
        viewModelScope.launch {
            getFinishedEvents()
        }
    }


    private suspend fun getFinishedEvents() {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEvents(0)
            val listEvents = response.listEvents
            _listEventsItem.value = listEvents
            _isLoading.value = false

        } catch (e: Exception) {
            _isLoading.value = false
            _snackBarText.value = Event("Failed to Load Data, Error: ${e.message}")
            Log.d(TAG, "${e.message}")
        }

    }

      fun searchFinishedEvents(query: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(0, query)
                val listEvents = response.listEvents
                _listEventsItem.value = listEvents
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
                _snackBarText.value = Event("Failed to Load Data, Error: ${e.message}")
                Log.d(TAG, "${e.message}")
            }
        }

    }
}