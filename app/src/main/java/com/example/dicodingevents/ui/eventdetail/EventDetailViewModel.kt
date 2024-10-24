package com.example.dicodingevents.ui.eventdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.data.remote.retrofit.ApiConfig
import com.example.dicodingevents.utils.Event
import kotlinx.coroutines.launch

class EventDetailViewModel (private val eventsRepository: EventsRepository)  : ViewModel() {

    fun getEventData(eventId: Int) = eventsRepository.getEventDetail(eventId)
}
