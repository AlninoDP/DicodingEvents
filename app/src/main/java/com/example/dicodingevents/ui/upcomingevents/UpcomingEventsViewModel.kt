package com.example.dicodingevents.ui.upcomingevents

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

class UpcomingEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getAllEvents() = eventsRepository.getAllEvents()

    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()

    fun searchEvent(name: String?, isFinished: Int? = 0) =
        eventsRepository.searchEvents(name, isFinished)

}