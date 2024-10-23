package com.example.dicodingevents.ui.eventdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.data.remote.retrofit.ApiConfig
import com.example.dicodingevents.utils.Event
import kotlinx.coroutines.launch

class EventDetailViewModel : ViewModel() {

    private val _eventsItem = MutableLiveData<ListEventsItem>()
    val eventsItem: LiveData<ListEventsItem> = _eventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    private var isDataLoaded = false


    companion object {
        private const val TAG = "eventDetailViewModel"
    }

    fun loadEventDetail(eventId: String) {
        if (!isDataLoaded) {
            viewModelScope.launch {
                getEventDetail(eventId)
            }
        }

    }

    private suspend fun getEventDetail(eventId: String) {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEventDetail(eventId)
            val event = response.event
            _eventsItem.value = event
            isDataLoaded = true
            _isLoading.value = false
        } catch (e: Exception) {
            _isLoading.value = false
            _snackBarText.value = Event("Failed to Load Data, Error: ${e.message}")
            Log.d(TAG, "${e.message}")
        }
    }

}
