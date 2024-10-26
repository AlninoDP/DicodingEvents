package com.example.dicodingevents.ui.bookmarkedevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class BookmarkedEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getBookmarkedEvents() = eventsRepository.getBookmarkedEvent()


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