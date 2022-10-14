package com.example.kotlinstart.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWorldWeatherFromLocalStorage():List<Weather>
    fun getRussianWeatherFromLocalStorage():List<Weather>
}