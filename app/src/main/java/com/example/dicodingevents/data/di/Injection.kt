package com.example.dicodingevents.data.di

import android.content.Context
import com.example.dicodingevents.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context){
        val apiService = ApiConfig.getApiService()

    }
}