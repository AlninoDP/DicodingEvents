package com.example.dicodingevents.ui.home

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.data.EventsRepository

class HomeViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getAllEvents() = eventsRepository.getAllEvents()

    fun getFinishedEvents() = eventsRepository.getFinishedEvents(limit = 5 )

    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()
}