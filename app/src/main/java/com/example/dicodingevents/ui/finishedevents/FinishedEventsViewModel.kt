package com.example.dicodingevents.ui.finishedevents

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.data.EventsRepository

class FinishedEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getAllEvents() = eventsRepository.getAllEvents()

    fun getFinishedEvents() = eventsRepository.getFinishedEvents()

    fun searchEvent(name: String?, isFinished: Int? = 1) =
        eventsRepository.searchEvents(name, isFinished)

}