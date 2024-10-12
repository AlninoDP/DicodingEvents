package com.example.dicodingevents.ui.upcomingevents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.data.response.ResponseEvents
import com.example.dicodingevents.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingEventsViewModel : ViewModel() {

    private val _listEventsItem = MutableLiveData<List<ListEventsItem>>()
    val listEventsItem: LiveData<List<ListEventsItem>> = _listEventsItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "upcomingEventsViewModel"
    }

    init {
        viewModelScope.launch {
            getUpcomingEvents()
        }
    }


    private suspend fun getUpcomingEvents() {
        _isLoading.value = true

        try {
            val response = ApiConfig.getApiService().getEvents(1)
            val listEvents = response.listEvents
            _listEventsItem.value = listEvents
            _isLoading.value = false

        } catch (e: Exception) {
            _isLoading.value = false
            Log.d(TAG, "${e.message}")
        }

    }

//    private fun getUpcomingEvents(): {
//        _isLoading.value = true
//
//        val client = ApiConfig.getApiService().getEvents(1)
//        client.enqueue(object : Callback<ResponseEvents> {
//
//            override fun onResponse(
//                call: Call<ResponseEvents>,
//                response: Response<ResponseEvents>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//
//                    if (responseBody != null){
//                        _listEventsItem.value = responseBody.listEvents
//                    }else{
//                        // TODO: Implement Error Handling
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseEvents>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }
}