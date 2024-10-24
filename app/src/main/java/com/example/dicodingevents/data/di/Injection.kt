package com.example.dicodingevents.data.di

import android.content.Context
import com.example.dicodingevents.data.EventsRepository
import com.example.dicodingevents.data.local.room.EventsDatabase
import com.example.dicodingevents.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventsRepository{
        val apiService = ApiConfig.getApiService()
        val database = EventsDatabase.getInstance(context)
        val dao = database.eventsDao()
        return EventsRepository.getInstance(apiService, dao)

    }
}