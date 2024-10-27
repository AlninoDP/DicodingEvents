package com.example.dicodingevents.data

sealed class Result<out R> private constructor() {
    class Success<out T> : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}