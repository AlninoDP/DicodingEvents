package com.example.dicodingevents.data.retrofit

import com.example.dicodingevents.data.response.ResponseEvents
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int? = -1,
        @Query("q") q: String? = null,
        @Query("limit") limit: Int? = 40,
    ): Call<ResponseEvents>


}