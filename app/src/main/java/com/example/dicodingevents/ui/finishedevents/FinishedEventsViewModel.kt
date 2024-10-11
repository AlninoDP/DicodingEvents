package com.example.dicodingevents.ui.finishedevents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FinishedEventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Finished Events Fragment"
    }
    val text: LiveData<String> = _text
}