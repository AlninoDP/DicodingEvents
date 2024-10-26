package com.example.dicodingevents.ui.eventdetail

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.data.EventsRepository

class EventDetailViewModel (private val eventsRepository: EventsRepository)  : ViewModel() {

    fun getEventData(eventId: Int) = eventsRepository.getEventDetail(eventId)
}
