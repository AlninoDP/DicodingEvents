package com.example.dicodingevents.ui.eventdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class EventDetailViewModel : ViewModel() {

    private val _eventsItem = MutableLiveData<ListEventsItem>()
    val eventsItem: LiveData<ListEventsItem> = _eventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isDataLoaded = false // New variable to track data loading state


    companion object {
        private const val TAG = "eventDetailViewModel"
    }

    fun loadEventDetail(eventId: String) {
        if (!isDataLoaded){
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
            // TODO: ADD BETTER HANDLING
            _isLoading.value = false
            Log.d(TAG, "${e.message}")
        }
    }

}
