package com.example.dicodingevents.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.di.Injection
import com.example.dicodingevents.ui.home.HomeViewModel

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
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}