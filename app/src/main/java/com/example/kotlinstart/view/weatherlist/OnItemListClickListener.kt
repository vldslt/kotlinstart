package com.example.kotlinstart.view.weatherlist

import com.example.kotlinstart.repository.Weather

interface OnItemListClickListener {
    fun onItemClick (weather:Weather)
}