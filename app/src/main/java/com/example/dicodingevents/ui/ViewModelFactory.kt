package com.example.dicodingevents.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevents.MainViewModel
import com.example.dicodingevents.SettingPreferences
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.di.Injection
import com.example.dicodingevents.datastore
import com.example.dicodingevents.ui.bookmarkedevents.BookmarkedEventsViewModel
import com.example.dicodingevents.ui.eventdetail.EventDetailViewModel
import com.example.dicodingevents.ui.finishedevents.FinishedEventsViewModel
import com.example.dicodingevents.ui.home.HomeViewModel
import com.example.dicodingevents.ui.settings.SettingsViewModel
import com.example.dicodingevents.ui.upcomingevents.UpcomingEventsViewModel

class ViewModelFactory private constructor(
    private val eventsRepository: EventsRepository,
    private val preferences: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

        companion object{
            @Volatile
            private var instance : ViewModelFactory? = null
            fun getInstance(context: Context) : ViewModelFactory =
                instance ?: synchronized(this){
                    val repository = Injection.provideRepository(context)
                    val preferences = SettingPreferences.getInstance(context.datastore)
                    instance ?: ViewModelFactory(repository, preferences)
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
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(preferences) as T
        }else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(preferences) as T
        }else if (modelClass.isAssignableFrom(BookmarkedEventsViewModel::class.java)){
            return BookmarkedEventsViewModel(eventsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}