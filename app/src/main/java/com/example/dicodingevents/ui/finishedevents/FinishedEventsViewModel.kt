package com.example.dicodingevents.ui.finishedevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class FinishedEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getAllEvents() = eventsRepository.getAllEvents()

    fun getFinishedEvents() = eventsRepository.getFinishedEvents()

    fun searchEvent(name: String?, isFinished: Int? = 1) =
        eventsRepository.searchEvents(name, isFinished)

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