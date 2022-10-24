package com.example.kotlinstart.viewmodel

import com.example.kotlinstart.repository.Weather

sealed class AppState {
    object Loading:AppState()
    data class Success(val weatherList:List<Weather>):AppState()
    data class Error(val error:Throwable):AppState()
}