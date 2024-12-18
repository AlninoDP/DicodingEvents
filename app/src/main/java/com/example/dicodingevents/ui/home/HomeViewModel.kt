package com.example.dicodingevents.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getAllEvents() = eventsRepository.getAllEvents()

    fun getFinishedEvents() = eventsRepository.getFinishedEvents(limit = 5 )

    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()

    fun bookmarkEvent(event: EventEntity) {
        viewModelScope.launch {
            eventsRepository.setEventsBookmark(event, true)
        }
    }

    fun unBookmarkEvent(event: EventEntity) {
        viewModelScope.launch {
            eventsRepository.setEventsBookmark(event, false)
        }
    }
}