package com.example.dicodingevents.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.di.Injection
import com.example.dicodingevents.ui.eventdetail.EventDetailViewModel
import com.example.dicodingevents.ui.finishedevents.FinishedEventsViewModel
import com.example.dicodingevents.ui.home.HomeViewModel
import com.example.dicodingevents.ui.upcomingevents.UpcomingEventsViewModel

class ViewModelFactory private constructor(private val eventsRepository: EventsRepository) :
    ViewModelProvider.NewInstanceFactory() {

        companion object{
            @Volatile
            private var instance : ViewModelFactory? = null
            fun getInstance(context: Context) : ViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }.also { instance = it }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(eventsRepository) as T
        } else if (modelClass.isAssignableFrom(UpcomingEventsViewModel::class.java)){
            return UpcomingEventsViewModel(eventsRepository) as T
        }else if (modelClass.isAssignableFrom(FinishedEventsViewModel::class.java)){
            return FinishedEventsViewModel(eventsRepository) as T
        }else if (modelClass.isAssignableFrom(EventDetailViewModel::class.java)){
            return EventDetailViewModel(eventsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}