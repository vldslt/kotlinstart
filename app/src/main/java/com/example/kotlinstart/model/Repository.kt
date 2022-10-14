package com.example.kotlinstart.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}