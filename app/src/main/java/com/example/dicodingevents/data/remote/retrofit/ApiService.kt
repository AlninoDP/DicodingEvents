package com.example.dicodingevents.data.remote.retrofit

import com.example.dicodingevents.data.remote.response.ResponseEvents
import retrofit2.http.GET 
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int? = -1,
        @Query("q") q: String? = null,
        @Query("limit") limit: Int? = 40,
    ): ResponseEvents
}