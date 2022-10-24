package com.example.kotlinstart.repository

import com.example.kotlinstart.repository.DTO.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)

}